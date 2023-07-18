package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.types.AutocreateMode;
import io.debezium.server.dist.builder.modules.types.BinaryHandlingMode;
import io.debezium.server.dist.builder.modules.types.DatabaseSslMode;
import io.debezium.server.dist.builder.modules.types.PostgresPlugins;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;

public class PostgresSourceNode implements ModuleNode {
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

    private DatabaseSslMode databaseSslMode;

    private String databaseSslcert;
    private String databaseSslkey;
    private String databaseSslpassword;
    private String databaseSslrootcert;

    private Boolean databaseTcpkeepalive;

    private AutocreateMode publicationAutocreateMode;

    //TODO: map this replica.identity.autoset.values

    private Integer moneyFractionDigits;

    private List<String> messagePrefixIncludeList;

    private List<String> messagePrefixExcludeList;


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
}
