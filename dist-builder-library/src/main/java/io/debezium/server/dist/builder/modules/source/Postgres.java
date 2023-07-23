package io.debezium.server.dist.builder.modules.source;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
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
        return new ModuleDependencyBuilder(document)
                .setGroupId(GROUP_ID)
                .setArtifactId(ARTIFACT_ID)
                .buildDependency();
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
        propertiesBuilder.put(debeziumServerSourcePrefix + "schema.include.list", String.join(",", schemaIncludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "schema.exclude.list", String.join(",", schemaExcludeList));

        try {
            propertiesBuilder.put(debeziumServerSourcePrefix + "hstore.handling.mode", new ObjectMapper().writeValueAsString(hstoreHandlingMode));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        propertiesBuilder.put(debeziumServerSourcePrefix + "database.sslmode", databaseSSLMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.sslcert", databaseSSLCert);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.sslkey", databaseSSLKey);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.sslpassword", databaseSSLPassword);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.sslrootcert", databaseSSLRootCert);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.tcpKeepAlive", databaseTCPKeepAlive);
        propertiesBuilder.put(debeziumServerSourcePrefix + "skip.messages.without.change", skipMessagesWithoutChange.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "publication.autocreate.mode", publicationAutocreateMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "replica.identity.autoset.values", replicaIdentityAutosetValues);
        propertiesBuilder.put(debeziumServerSourcePrefix + "money.fraction.digits", moneyFractionDigits);
        propertiesBuilder.put(debeziumServerSourcePrefix + "message.prefix.include.list", String.join(",", messagePrefixIncludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "message.prefix.exclude.list", String.join(",", messagePrefixExcludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.locking.mode", snapshotLockingMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.custom.class", snapshotCustomClass);
        propertiesBuilder.put(debeziumServerSourcePrefix + "poll.interval.ms", pollIntervalMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "include.unknown.datatypes", includeUnknownDatatypes.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.initial.statements", String.join(",", databaseInitialStatements));
        propertiesBuilder.put(debeziumServerSourcePrefix + "status.update.interval.ms", statusUpdateIntervalMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "heartbeat.action.query", heartbeatActionQuery);
        propertiesBuilder.put(debeziumServerSourcePrefix + "schema.refresh.mode", schemaRefreshMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "slot.stream.params", String.join(",", slotStreamParams));
        propertiesBuilder.put(debeziumServerSourcePrefix + "slot.max.retries", slotMaxRetries);
        propertiesBuilder.put(debeziumServerSourcePrefix + "slot.retry.delay.ms", slotRetryDelayMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "unavailable.value.placeholder", unavailableValuePlaceholder);
        propertiesBuilder.put(debeziumServerSourcePrefix + "provide.transaction.metadata", provideTransactionMetadata.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "flush.lsn.source", flushLsnSource.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "retriable.restart.connector.wait.ms", retriableRestartConnectorWaitMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "xmin.fetch.interval.ms", xminFetchIntervalMs);


        return propertiesBuilder.getProperties();
    }
}
