package dev.mikolajk.watchnext;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import java.util.Map;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.testcontainers.containers.MariaDBContainer;

public class MariaDbContainerResource implements QuarkusTestResourceLifecycleManager {

    private static final MariaDBContainer<?> MARIA_DB_CONTAINER = new MariaDBContainer<>();

    @Override
    public Map<String, String> start() {
        MARIA_DB_CONTAINER.start();
        return Map.of(
            "quarkus.datasource.jdbc.url", MARIA_DB_CONTAINER.getJdbcUrl(),
            "quarkus.datasource.username", MARIA_DB_CONTAINER.getUsername(),
            "quarkus.datasource.password", MARIA_DB_CONTAINER.getPassword()
        );
    }

    @Override
    public void stop() {
        MARIA_DB_CONTAINER.stop();
    }
}
