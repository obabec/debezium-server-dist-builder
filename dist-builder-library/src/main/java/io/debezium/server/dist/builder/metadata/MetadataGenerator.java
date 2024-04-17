/*
 * MIT License
 *
 * Copyright (c) [2024] [Ondrej Babec <ond.babec@gmail.com>]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE
 * ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.debezium.server.dist.builder.metadata;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.server.dist.builder.CustomDebeziumServer;
import io.debezium.server.dist.builder.modules.SourceNode;
import org.reflections.Reflections;

public class MetadataGenerator {

    public void generateMetadata(OutputStream fileOutputStream) throws ClassNotFoundException, IOException {
        MetadataModelObject metadataModelObject = new MetadataModelObject();
        printFields(CustomDebeziumServer.class, metadataModelObject, null);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.writerWithDefaultPrettyPrinter().writeValue(fileOutputStream, metadataModelObject);
    }

    public void generateSourceNodeMetadata(OutputStream fileOutputStream) throws ClassNotFoundException, IOException {
        MetadataModelObject metadataModelObject = new MetadataModelObject();
        printFields(SourceNode.class, metadataModelObject, null);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.writerWithDefaultPrettyPrinter().writeValue(fileOutputStream, metadataModelObject);
    }

    private void processInterfaceClass(Class<?> c, MetadataModelObject metadataModelObject) throws ClassNotFoundException {
        Reflections reflections = new Reflections("io.debezium");
        Object[] rawObjectArray = reflections.getSubTypesOf(c).toArray();
        List<Object> objectAttributeList = Arrays.asList(rawObjectArray);


        List<MetadataModelObject> arr = new ArrayList<>();

        for (Object o : objectAttributeList) {
            MetadataModelObject mo = new MetadataModelObject();
            Object[] implementationList;
            if (((Class<?>) o).isInterface()) {
                implementationList = reflections.getSubTypesOf(((Class<?>) o)).toArray();
                for (Object implementation : implementationList) {
                    if (!objectAttributeList.contains(implementation)) {
                        objectAttributeList.add(implementation);
                    }
                }
                continue;
            }

            if (((Class<?>) o).getName().contains("Editable")) {
                continue;
            }
            if (((Class<?>) o).getName().contains("io.debezium")) {
                mo.setType("class");
                String name = ((Class<?>) o).getName();
                mo.setName(name.substring(name.lastIndexOf('.') + 1));
            }

            printFields(((Class<?>) o), mo, null);

            arr.add(mo);
        }

        metadataModelObject.setOptions(arr);
    }

    private void printFields(Class<?> c, MetadataModelObject metadataModelObject, String name) throws ClassNotFoundException {
        if (!c.isInterface()) {
            metadataModelObject.setClazz(c.getTypeName());
            if (name == null) {
                metadataModelObject.setName(c.getTypeName().substring(
                        c.getTypeName().lastIndexOf('.') + 1));
            } else {
                metadataModelObject.setName(name);
            }

            if (c.getTypeName().contains("io.debezium")) {
                metadataModelObject.setType("class");
            }
        }

        if (c.isInterface()) {
            metadataModelObject.setType("interface");
            processInterfaceClass(c, metadataModelObject);
            return;
        }
        // Enum
        if (c.isEnum()) {
            metadataModelObject.setType("enum");
            List<String> variants = new ArrayList<>();
            for (Object s : c.getEnumConstants()) {
                variants.add(s.toString());
            }
            metadataModelObject.setVariants(variants);
            return;
        }

        List<MetadataModelObject> fields = new ArrayList<>();

        List<Field> currentFields = new ArrayList<>();
        Class<?> clazz = c;
        while (clazz != Object.class) {
            currentFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        for (Field f : currentFields) {
            if (Modifier.isFinal(f.getModifiers())) continue;
            if (f.getAnnotatedType().toString().contains("io.debezium.operator.api.model.DebeziumServer")) {
                continue;
            }
            MetadataModelObject field = new MetadataModelObject();
            field.setName(f.getName());
            String type = f.getAnnotatedType().toString();
            if (type.contains("io.debezium")) {
                field.setType("class");
            } else {
                field.setType(f.getAnnotatedType().toString());
            }


            if (f.getAnnotatedType().toString().contains("io.debezium")) {
                Class<?> x = ClassLoader.getSystemClassLoader().loadClass(f.getType().getName());
                if (x.isEnum()) {
                    field.setType("enum");
                    List<String> variants = new ArrayList<>();

                    for (Object s : x.getEnumConstants()) {
                        variants.add(s.toString());
                    }
                    field.setVariants(variants);
                } else if (x.isInterface()) {
                    if (x.getTypeName().contains("List")) {
                        field.setType("DependencyList");
                    } else {
                        field.setType("interface");
                        processInterfaceClass(x, field);
                    }
                } else {
                    printFields(x, field, field.name);
                }
            }
            fields.add(field);
        }
        metadataModelObject.setFields(fields);
    }

}
