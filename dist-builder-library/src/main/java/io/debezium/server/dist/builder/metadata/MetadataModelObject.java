package io.debezium.server.dist.builder.metadata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetadataModelObject {
    String name;
    String type;
    String clazz;
    List<MetadataModelObject> fields;
    List<MetadataModelObject> options;
    List<String> variants;
    Boolean selected;
    String value;


    public MetadataModelObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public List<MetadataModelObject> getFields() {
        return fields;
    }

    public void setFields(List<MetadataModelObject> fields) {
        this.fields = fields;
    }

    public List<MetadataModelObject> getOptions() {
        return options;
    }

    public void setOptions(List<MetadataModelObject> options) {
        this.options = options;
    }

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
