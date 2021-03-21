package dev.mikolajk.watchnext.keycloak;

import io.quarkus.arc.config.ConfigProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigProperties(prefix = "quarkus.keycloak")
public class KeycloakConfiguration {

    private String username;
    private String password;
    private String usersRealm;
    private String managementRealm;
    private String url;
    private String clientId;

}
