package dev.mikolajk.watchnext.keycloak;

import dev.mikolajk.watchnext.service.model.user.UserProfile;
import java.util.List;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "cdi")
public interface KeycloakMapper {

    @Mapping(target = "name", source = "username")
    @Named("toUserProfile")
    UserProfile toUserProfile(UserRepresentation userResource);

    @IterableMapping(qualifiedByName = "toUserProfile")
    List<UserProfile> toUserProfiles(List<UserRepresentation> userResource);
}
