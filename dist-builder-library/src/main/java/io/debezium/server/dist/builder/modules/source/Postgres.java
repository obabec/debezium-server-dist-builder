package io.debezium.server.dist.builder.modules.source;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.debezium.server.dist.builder.modules.config.sources.SqlBasedConnectorConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.AutocreateMode;
import io.debezium.server.dist.builder.modules.config.sources.types.DatabaseSslMode;
import io.debezium.server.dist.builder.modules.config.sources.types.PostgresPlugins;
import io.debezium.server.dist.builder.modules.config.sources.types.SchemaRefreshMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotLockingMode;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@Buildable
@Getter
@Setter
public class Postgres extends SqlBasedConnectorConfig implements SourceNode {
    private final String ARTIFACT_ID = DEBEZIUM_CONNECTOR_PREFIX + "postgres";

    private final String connectorClass = "io.debezium.connector.postgresql.PostgresConnector";

    private PostgresPlugins pluginName;

    private String slotName;

    private Boolean slotDropOnStop;

    private String publicationName;

    private String databaseDbname;

    private List<String> schemaIncludeList;
    private List<String> schemaExcludeList;

    private HashMap<String, String> hstoreHandlingMode;

    private DatabaseSslMode databaseSSLMode;
    private Boolean skipMessagesWithoutChange;
    private String databaseSSLCert;
    private String databaseSSLKey;
    private String databaseSSLPassword;
    private String databaseSSLRootCert;

    private Boolean databaseTCPKeepAlive;

    private AutocreateMode publicationAutocreateMode;

    private String replicaIdentityAutosetValues;

    private Integer moneyFractionDigits;

    private List<String> messagePrefixIncludeList;

    private List<String> messagePrefixExcludeList;

    private SnapshotLockingMode snapshotLockingMode;

    private String snapshotCustomClass;

    private Integer pollIntervalMs;

    private Boolean includeUnknownDatatypes;

    private List<String> databaseInitialStatements;

    private Integer statusUpdateIntervalMs;

    private String heartbeatActionQuery;

    private SchemaRefreshMode schemaRefreshMode;

    private List<String> slotStreamParams;

    private Integer slotMaxRetries;

    private Integer slotRetryDelayMs;

    private String unavailableValuePlaceholder;

    private Boolean provideTransactionMetadata;

    private Boolean flushLsnSource;

    private Integer retriableRestartConnectorWaitMs;

    private Integer xminFetchIntervalMs;

    public Postgres() {
    }

    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    @Override
    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put(debeziumServerSourcePrefix + "plugin.name", pluginName);
        builder.put(debeziumServerSourcePrefix + "slot.name", slotName);
        builder.put(debeziumServerSourcePrefix + "slot.drop.on.stop", slotDropOnStop);
        builder.put(debeziumServerSourcePrefix + "publication.name", publicationName);
        builder.put(debeziumServerSourcePrefix + "database.dbname", databaseDbname);
        builder.putList(debeziumServerSourcePrefix + "schema.include.list", schemaIncludeList);
        builder.putList(debeziumServerSourcePrefix + "schema.exclude.list", schemaExcludeList);

        try {
            builder.put(debeziumServerSourcePrefix + "hstore.handling.mode", new ObjectMapper().writeValueAsString(hstoreHandlingMode));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "database.sslmode", databaseSSLMode);
        builder.put(debeziumServerSourcePrefix + "database.sslcert", databaseSSLCert);
        builder.put(debeziumServerSourcePrefix + "database.sslkey", databaseSSLKey);
        builder.put(debeziumServerSourcePrefix + "database.sslpassword", databaseSSLPassword);
        builder.put(debeziumServerSourcePrefix + "database.sslrootcert", databaseSSLRootCert);
        builder.put(debeziumServerSourcePrefix + "database.tcpKeepAlive", databaseTCPKeepAlive);
        builder.putBoolean(debeziumServerSourcePrefix + "skip.messages.without.change", skipMessagesWithoutChange);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "publication.autocreate.mode", publicationAutocreateMode);
        builder.put(debeziumServerSourcePrefix + "replica.identity.autoset.values", replicaIdentityAutosetValues);
        builder.put(debeziumServerSourcePrefix + "money.fraction.digits", moneyFractionDigits);
        builder.putList(debeziumServerSourcePrefix + "message.prefix.include.list", messagePrefixIncludeList);
        builder.putList(debeziumServerSourcePrefix + "message.prefix.exclude.list", messagePrefixExcludeList);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "snapshot.locking.mode", snapshotLockingMode);
        builder.put(debeziumServerSourcePrefix + "snapshot.custom.class", snapshotCustomClass);
        builder.put(debeziumServerSourcePrefix + "poll.interval.ms", pollIntervalMs);
        builder.putBoolean(debeziumServerSourcePrefix + "include.unknown.datatypes", includeUnknownDatatypes);
        builder.putList(debeziumServerSourcePrefix + "database.initial.statements", databaseInitialStatements);
        builder.put(debeziumServerSourcePrefix + "status.update.interval.ms", statusUpdateIntervalMs);
        builder.put(debeziumServerSourcePrefix + "heartbeat.action.query", heartbeatActionQuery);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "schema.refresh.mode", schemaRefreshMode);
        builder.putList(debeziumServerSourcePrefix + "slot.stream.params", slotStreamParams);
        builder.put(debeziumServerSourcePrefix + "slot.max.retries", slotMaxRetries);
        builder.put(debeziumServerSourcePrefix + "slot.retry.delay.ms", slotRetryDelayMs);
        builder.put(debeziumServerSourcePrefix + "unavailable.value.placeholder", unavailableValuePlaceholder);
        builder.putBoolean(debeziumServerSourcePrefix + "provide.transaction.metadata", provideTransactionMetadata);
        builder.putBoolean(debeziumServerSourcePrefix + "flush.lsn.source", flushLsnSource);
        builder.put(debeziumServerSourcePrefix + "retriable.restart.connector.wait.ms", retriableRestartConnectorWaitMs);
        builder.put(debeziumServerSourcePrefix + "xmin.fetch.interval.ms", xminFetchIntervalMs);
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getCommonConfig(yamlBuilder);
        return yamlBuilder.getYaml();
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        propertiesBuilder.put(debeziumServerSourcePrefix + "connector.class", connectorClass);
        getCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }
}
