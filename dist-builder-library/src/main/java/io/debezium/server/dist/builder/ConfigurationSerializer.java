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

package io.debezium.server.dist.builder;


import java.util.Properties;

/**
 * ConfigurationSerializer serializes the properties to Java properties style key=value.
 */
public class ConfigurationSerializer {
    /**
     * Serializes whole CustomDebeziumServer configuration to Java properties style key=value.
     * @param customDebeziumServer Custom Debezium Server object
     * @return Serialized String
     */
    static public String configToPropertiesFile(CustomDebeziumServer customDebeziumServer) {
        StringBuilder stringBuilder = new StringBuilder();
        Properties properties = customDebeziumServer.toProperties();

        for (Object key : properties.keySet()) {
            String k = key.toString();
            stringBuilder.append(k);
            stringBuilder.append("=");
            stringBuilder.append(properties.get(k).toString());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
