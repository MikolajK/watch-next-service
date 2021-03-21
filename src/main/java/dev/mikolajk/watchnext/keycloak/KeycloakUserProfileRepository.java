package dev.mikolajk.watchnext.keycloak;

import dev.mikolajk.watchnext.persistence.UserProfileRepository;
import dev.mikolajk.watchnext.service.errors.ApiErrorMessages;
import dev.mikolajk.watchnext.service.model.user.UserProfile;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;

@ApplicationScoped
public class KeycloakUserProfileRepository implements UserProfileRepository {

    private final RealmResource realmResource;
    private final KeycloakMapper keycloakMapper;

    @Inject
    public KeycloakUserProfileRepository(RealmResource realmResource, KeycloakMapper keycloakMapper) {
        this.realmResource = realmResource;
        this.keycloakMapper = keycloakMapper;
    }

    @Override
    public UserProfile getUserById(String userId) {
        List<UserRepresentation> users = realmResource.users().search(userId, true);
        if (users == null) {
            throw new InternalServerErrorException(ApiErrorMessages.USER_NOT_FOUND_IN_DATABASE);
        }

        if (users.isEmpty()) {
            throw new InternalServerErrorException(ApiErrorMessages.NO_USER_FOUND);
        }

        if (users.size() > 1) {
            throw new InternalServerErrorException(ApiErrorMessages.KEYCLOAK_API_ERROR);
        }

        return keycloakMapper.toUserProfile(users.get(0));
    }

    @Override
    public List<UserProfile> getUsers(List<String> userIds) {
        // Not very performant but not expecting more than 2-3 users for this project and not sure Keycloak can do it
        // better
        return userIds.stream().map(this::getUserById).collect(Collectors.toList());
    }
}
