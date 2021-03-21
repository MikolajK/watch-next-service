package dev.mikolajk.watchnext.keycloak;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;

@Dependent
public class KeycloakRealmResourceProvider {

    private final KeycloakConfiguration keycloakConfiguration;

    @Inject
    public KeycloakRealmResourceProvider(KeycloakConfiguration keycloakConfiguration) {
        this.keycloakConfiguration = keycloakConfiguration;
    }

    @Produces
    public RealmResource realmResource() {
        return KeycloakBuilder.builder()
            .serverUrl(keycloakConfiguration.getUrl())
            .realm(keycloakConfiguration.getManagementRealm())
            .clientId(keycloakConfiguration.getClientId())
            .username(keycloakConfiguration.getUsername())
            .password(keycloakConfiguration.getPassword())
            .grantType(OAuth2Constants.PASSWORD)
            .build().realm(keycloakConfiguration.getUsersRealm());
    }


}
