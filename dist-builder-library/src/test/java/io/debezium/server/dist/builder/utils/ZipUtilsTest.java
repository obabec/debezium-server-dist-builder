package io.debezium.server.dist.builder.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ZipUtilsTest {

    @Test
    void zipFolder() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ZipUtils.zipFolder(Path.of(this.getClass().getClassLoader().getResource("folder-to-zip").getPath()), bos);
            InputStream is = this.getClass().getClassLoader().getResource("test.zip").openStream();
            assertThat(is.readAllBytes(), is(equalTo(bos.toByteArray())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}