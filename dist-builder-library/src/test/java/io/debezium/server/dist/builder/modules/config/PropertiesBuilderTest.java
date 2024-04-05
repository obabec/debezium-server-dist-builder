package io.debezium.server.dist.builder.modules.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import io.debezium.server.dist.builder.modules.config.sources.logmine.Strategy;
import io.debezium.server.dist.builder.modules.sink.Http;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class PropertiesBuilderTest {

    @Test
    void put() {
        Properties props = new Properties();
        props.put("test-key", "value");
        PropertiesBuilder pb = new PropertiesBuilder();
        pb.put("test-key", "value");
        pb.put("test-null", null);
        assertThat(pb.getProperties(), is(equalTo(props)));
    }

    @Test
    void putEnumWithLowerCase() {
        Properties props = new Properties();
        props.put("test-key", "online_catalog");
        PropertiesBuilder pb = new PropertiesBuilder();
        pb.putEnumWithLowerCase("test-key", Strategy.ONLINE_CATALOG);
        pb.putEnumWithLowerCase("test-null", null);
        assertThat(pb.getProperties(), is(equalTo(props)));
    }

    @Test
    void putEnum() {
        Properties props = new Properties();
        props.put("test-key", "ONLINE_CATALOG");
        PropertiesBuilder pb = new PropertiesBuilder();
        pb.putEnum("test-key", Strategy.ONLINE_CATALOG);
        pb.putEnumWithLowerCase("test-null", null);
        assertThat(pb.getProperties(), is(equalTo(props)));
    }

    @Test
    void putList() {
        List<String> items = new ArrayList<>();
        items.add("item1");
        items.add("item2");
        PropertiesBuilder pb = new PropertiesBuilder();
        pb.putList("test-list", items);
        pb.putList("test-null", null);
        Properties props = new Properties();
        props.put("test-list", "item1,item2");
        assertThat(pb.getProperties(), is(equalTo(props)));
    }

    @Test
    void putBoolean() {
        PropertiesBuilder pb = new PropertiesBuilder();
        pb.putBoolean("test-bool", false);
        pb.putBoolean("test-null", null);
        Properties props = new Properties();
        props.put("test-bool", false);
        assertThat(pb.getProperties(), is(equalTo(props)));
    }

    @Test
    void putAll() {
        Http http = new Http();
        http.setHttpUrl("test-url");
        http.setHttpRetries(5);
        PropertiesBuilder pb = new PropertiesBuilder();
        pb.put("test-key", "test-value");
        pb.putAll(http);
        Properties props = new Properties();
        props.put("test-key", "test-value");
        props.put("debezium.sink.http.url", "test-url");
        props.put("debezium.sink.http.retries", 5);
        props.put("debezium.sink.type", "http");
        assertThat(pb.getProperties(), is(equalTo(props)));
    }

    @Test
    void putAllWithPrefix() {
        Http http = new Http();
        http.setHttpUrl("test-url");
        http.setHttpRetries(5);
        PropertiesBuilder pb = new PropertiesBuilder();
        pb.put("test-key", "test-value");
        pb.putAllWithPrefix("prefix.", http);
        Properties props = new Properties();
        props.put("test-key", "test-value");
        props.put("prefix.debezium.sink.http.url", "test-url");
        props.put("prefix.debezium.sink.http.retries", 5);
        props.put("prefix.debezium.sink.type", "http");
        assertThat(pb.getProperties(), is(equalTo(props)));
    }

    @Test
    void putAllWithPrefixMap() {
        Http http = new Http();
        http.setHttpUrl("test-url");
        http.setHttpRetries(5);
        PropertiesBuilder pb = new PropertiesBuilder();
        pb.put("test-key", "test-value");
        pb.putAllWithPrefix("prefix.", http.toYaml());
        Properties props = new Properties();
        props.put("test-key", "test-value");
        props.put("prefix.debezium.sink.http.url", "test-url");
        props.put("prefix.debezium.sink.http.retries", 5);
        assertThat(pb.getProperties(), is(equalTo(props)));
    }
}