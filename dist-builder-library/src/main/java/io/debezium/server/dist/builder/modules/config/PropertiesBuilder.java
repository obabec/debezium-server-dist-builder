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

package io.debezium.server.dist.builder.modules.config;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import lombok.Getter;

/**
 * PropertiesBuilder is used to handle all unexpected situations that can occur during properties build.
 */
@Getter
public class PropertiesBuilder implements ConfigBuilder<PropertiesConfig> {
    private final Properties properties;

    public PropertiesBuilder(Properties properties) {
        this.properties = properties;
    }

    public PropertiesBuilder() {
        this.properties = new Properties();
    }

    @Override
    public void put(String key, Object value) {
        if (value != null) {
            properties.put(key, value);
        }
    }

    @Override
    public <E extends Enum<E>> void putEnumWithLowerCase(String key, E e) {
        if (e != null) {
            properties.put(key, e.toString().toLowerCase());
        }
    }

    @Override
    public <E extends Enum<E>> void putEnum(String key, E e) {
        if (e != null) {
            properties.put(key, e.toString());
        }
    }

    @Override
    public void putList(String key, List<String> list) {
        if (list != null && !list.isEmpty()) {
            properties.put(key, String.join(",", list));
        }
    }

    @Override
    public void putBoolean(String key, Boolean value) {
        if (value != null) {
            properties.put(key, value);
        }
    }

    @Override
    public void putAll(PropertiesConfig p) {
        if (p != null) {
            properties.putAll(p.toProperties());
        }
    }

    @Override
    public void putAllWithPrefix(String prefix, PropertiesConfig p) {
        if (p != null) {
            Properties props = p.toProperties();
            for (Object key : props.keySet()) {
                properties.put(prefix + key, props.get(key));
            }
        }
    }

    @Override
    public void putAllWithPrefix(String prefix, Map<String, Object> map) {
        if (map != null) {
            for (String key : map.keySet()) {
                properties.put(prefix + key, map.get(key));
            }
        }
    }
}
