package io.debezium.server.dist.builder.modules.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.debezium.server.dist.builder.modules.config.sources.logmine.Strategy;
import io.debezium.server.dist.builder.modules.sink.Http;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class YamlBuilderTest {
    @Test
    void put() {
        HashMap<String, Object> props = new HashMap<>();
        props.put("test-key", "value");
        YamlBuilder pb = new YamlBuilder();
        pb.put("test-key", "value");
        pb.put("test-null", null);
        assertThat(pb.getYaml(), is(equalTo(props)));
    }

    @Test
    void putEnumWithLowerCase() {
        HashMap<String, Object> props = new HashMap<>();
        props.put("test-key", "online_catalog");
        YamlBuilder pb = new YamlBuilder();
        pb.putEnumWithLowerCase("test-key", Strategy.ONLINE_CATALOG);
        pb.putEnumWithLowerCase("test-null", null);
        assertThat(pb.getYaml(), is(equalTo(props)));
    }

    @Test
    void putEnum() {
        HashMap<String, Object> props = new HashMap<>();
        props.put("test-key", "ONLINE_CATALOG");
        YamlBuilder pb = new YamlBuilder();
        pb.putEnum("test-key", Strategy.ONLINE_CATALOG);
        pb.putEnumWithLowerCase("test-null", null);
        assertThat(pb.getYaml(), is(equalTo(props)));
    }

    @Test
    void putList() {
        List<String> items = new ArrayList<>();
        items.add("item1");
        items.add("item2");
        YamlBuilder pb = new YamlBuilder();
        pb.putList("test-list", items);
        pb.putList("test-null", null);
        HashMap<String, Object> props = new HashMap<>();
        props.put("test-list", "item1,item2");
        assertThat(pb.getYaml(), is(equalTo(props)));
    }

    @Test
    void putBoolean() {
        YamlBuilder pb = new YamlBuilder();
        pb.putBoolean("test-bool", false);
        pb.putBoolean("test-null", null);
        HashMap<String, Object> props = new HashMap<>();
        props.put("test-bool", "false");
        assertThat(pb.getYaml(), is(equalTo(props)));
    }

    @Test
    void putAll() {
        Http http = new Http();
        http.setHttpUrl("test-url");
        http.setHttpRetries(5);
        YamlBuilder pb = new YamlBuilder();
        pb.put("test-key", "test-value");
        pb.putAll(http);
        HashMap<String, Object> props = new HashMap<>();
        props.put("test-key", "test-value");
        props.put("debezium.sink.http.url", "test-url");
        props.put("debezium.sink.http.retries", 5);
        assertThat(pb.getYaml(), is(equalTo(props)));
    }

    @Test
    void putAllWithPrefix() {
        Http http = new Http();
        http.setHttpUrl("test-url");
        http.setHttpRetries(5);
        YamlBuilder pb = new YamlBuilder();
        pb.put("test-key", "test-value");
        pb.putAllWithPrefix("prefix.", http);
        HashMap<String, Object> props = new HashMap<>();
        props.put("test-key", "test-value");
        props.put("prefix.debezium.sink.http.url", "test-url");
        props.put("prefix.debezium.sink.http.retries", 5);
        assertThat(pb.getYaml(), is(equalTo(props)));
    }

    @Test
    void putAllWithPrefixMap() {
        Http http = new Http();
        http.setHttpUrl("test-url");
        http.setHttpRetries(5);
        YamlBuilder pb = new YamlBuilder();
        pb.put("test-key", "test-value");
        pb.putAllWithPrefix("prefix.", http.toYaml());
        HashMap<String, Object> props = new HashMap<>();
        props.put("test-key", "test-value");
        props.put("prefix.debezium.sink.http.url", "test-url");
        props.put("prefix.debezium.sink.http.retries", 5);
        assertThat(pb.getYaml(), is(equalTo(props)));
    }
}