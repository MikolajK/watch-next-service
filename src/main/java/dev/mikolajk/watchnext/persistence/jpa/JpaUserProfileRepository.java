package dev.mikolajk.watchnext.persistence.jpa;

import dev.mikolajk.watchnext.persistence.UserProfileRepository;
import dev.mikolajk.watchnext.persistence.model.user.UserProfileEntity;
import javax.enterprise.context.ApplicationScoped;

/**
 * TODO: Authentication
 */
@ApplicationScoped
public class JpaUserProfileRepository implements UserProfileRepository {

    @Override
    public UserProfileEntity getUserById(String userId) {
        if ("dummy".equals(userId)) {
            return new UserProfileEntity();
        }

        return null;
    }
}
