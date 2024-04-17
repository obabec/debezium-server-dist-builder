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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

/**
 * YamlBuilder is used to handle all unexpected situations that can occur during yaml build.
 */
@Getter
public class YamlBuilder implements ConfigBuilder<YamlConfig> {
    private final HashMap<String, Object> yaml;

    public YamlBuilder(HashMap<String, Object> yaml) {
        this.yaml = yaml;
    }

    public YamlBuilder() {
        this.yaml = new HashMap<>();
    }

    @Override
    public void put(String key, Object value) {
        if (value != null) {
            yaml.put(key, value);
        }
    }

    @Override
    public <E extends Enum<E>> void putEnumWithLowerCase(String key, E e) {
        if (e != null) {
            yaml.put(key, e.toString().toLowerCase());
        }
    }

    @Override
    public <E extends Enum<E>> void putEnum(String key, E e) {
        if (e != null) {
            yaml.put(key, e.toString());
        }
    }

    @Override
    public void putList(String key, List<String> list) {
        if (list != null && !list.isEmpty()) {
            yaml.put(key, String.join(",", list));
        }
    }

    @Override
    public void putBoolean(String key, Boolean value) {
        if (value != null) {
            yaml.put(key, value.toString());
        }
    }

    @Override
    public void putAll(YamlConfig p) {
        if (p != null) {
            yaml.putAll(p.toYaml());
        }
    }

    public void putAllMap(HashMap<String, Object> p) {
        if (p != null) {
            yaml.putAll(p);
        }
    }

    @Override
    public void putAllWithPrefix(String prefix, YamlConfig p) {
        HashMap<String, Object> objectHashMap = p.toYaml();
        if (objectHashMap != null) {
            for (Map.Entry<String, Object> entry : objectHashMap.entrySet()) {
                yaml.put(prefix + entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void putAllWithPrefix(String prefix, Map<String, Object> map) {
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                yaml.put(prefix + entry.getKey(), entry.getValue());
            }
        }
    }

    public void putAllWithKey(String key, YamlConfig p) {
        if (p != null) {
            yaml.put(key, p.toYaml());
        }
    }

    public void putAllWithKeyAndPrefix(String key, String prefix, YamlConfig p) {
        Map<String, Object> newMap = new HashMap<>();
        Map<String, Object> objectHashMap = p.toYaml();
        if (objectHashMap != null) {
            for (Map.Entry<String, Object> entry : objectHashMap.entrySet()) {
                newMap.put(prefix + entry.getKey(), entry.getValue());
            }
        }
        yaml.put(key, newMap);
    }
}
