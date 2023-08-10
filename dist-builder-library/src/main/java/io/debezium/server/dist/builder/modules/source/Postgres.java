package io.debezium.server.dist.builder.modules.source;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
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

    @Override
    public Node buildNode(Document document) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        propertiesBuilder.put(debeziumServerSourcePrefix + "connector.class", connectorClass);
        propertiesBuilder.put(debeziumServerSourcePrefix + "plugin.name", pluginName);
        propertiesBuilder.put(debeziumServerSourcePrefix + "slot.name", slotName);
        propertiesBuilder.put(debeziumServerSourcePrefix + "slot.drop.on.stop", slotDropOnStop);
        propertiesBuilder.put(debeziumServerSourcePrefix + "publication.name", publicationName);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.dbname", databaseDbname);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "schema.include.list", schemaIncludeList);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "schema.exclude.list", schemaExcludeList);

        try {
            propertiesBuilder.put(debeziumServerSourcePrefix + "hstore.handling.mode", new ObjectMapper().writeValueAsString(hstoreHandlingMode));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "database.sslmode", databaseSSLMode);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.sslcert", databaseSSLCert);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.sslkey", databaseSSLKey);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.sslpassword", databaseSSLPassword);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.sslrootcert", databaseSSLRootCert);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.tcpKeepAlive", databaseTCPKeepAlive);
        propertiesBuilder.putBoolean(debeziumServerSourcePrefix + "skip.messages.without.change", skipMessagesWithoutChange);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "publication.autocreate.mode", publicationAutocreateMode);
        propertiesBuilder.put(debeziumServerSourcePrefix + "replica.identity.autoset.values", replicaIdentityAutosetValues);
        propertiesBuilder.put(debeziumServerSourcePrefix + "money.fraction.digits", moneyFractionDigits);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "message.prefix.include.list", messagePrefixIncludeList);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "message.prefix.exclude.list", messagePrefixExcludeList);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "snapshot.locking.mode", snapshotLockingMode);
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.custom.class", snapshotCustomClass);
        propertiesBuilder.put(debeziumServerSourcePrefix + "poll.interval.ms", pollIntervalMs);
        propertiesBuilder.putBoolean(debeziumServerSourcePrefix + "include.unknown.datatypes", includeUnknownDatatypes);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "database.initial.statements", databaseInitialStatements);
        propertiesBuilder.put(debeziumServerSourcePrefix + "status.update.interval.ms", statusUpdateIntervalMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "heartbeat.action.query", heartbeatActionQuery);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "schema.refresh.mode", schemaRefreshMode);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "slot.stream.params", slotStreamParams);
        propertiesBuilder.put(debeziumServerSourcePrefix + "slot.max.retries", slotMaxRetries);
        propertiesBuilder.put(debeziumServerSourcePrefix + "slot.retry.delay.ms", slotRetryDelayMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "unavailable.value.placeholder", unavailableValuePlaceholder);
        propertiesBuilder.putBoolean(debeziumServerSourcePrefix + "provide.transaction.metadata", provideTransactionMetadata);
        propertiesBuilder.putBoolean(debeziumServerSourcePrefix + "flush.lsn.source", flushLsnSource);
        propertiesBuilder.put(debeziumServerSourcePrefix + "retriable.restart.connector.wait.ms", retriableRestartConnectorWaitMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "xmin.fetch.interval.ms", xminFetchIntervalMs);


        return propertiesBuilder.getProperties();
    }
}
