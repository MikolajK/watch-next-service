package dev.mikolajk.watchnext.persistence;

import dev.mikolajk.watchnext.persistence.model.user.UserProfileEntity;

public interface UserProfileRepository {

    UserProfileEntity getUserById(String userId);

}
