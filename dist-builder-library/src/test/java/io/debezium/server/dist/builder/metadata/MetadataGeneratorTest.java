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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MetadataGeneratorTest {

    @Test
    void generateMetadata() throws IOException, ClassNotFoundException {
        MetadataGenerator metadataGenerator = new MetadataGenerator();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("main-metadata.json");

        metadataGenerator.generateMetadata(bos);
        assertThat(is.readAllBytes(), is(equalTo(bos.toByteArray())));
    }

    @Test
    void generateSourceNodeMetadata() throws IOException, ClassNotFoundException {
        MetadataGenerator metadataGenerator = new MetadataGenerator();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("metadata-source-js.json");

        metadataGenerator.generateSourceNodeMetadata(bos);
        assertThat(is.readAllBytes(), is(equalTo(bos.toByteArray())));
    }
}