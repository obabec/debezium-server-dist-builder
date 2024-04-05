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