package dev.mikolajk.watchnext.persistence;

import dev.mikolajk.watchnext.persistence.model.user.UserProfileEntity;
import java.util.List;

public interface UserProfileRepository {

    UserProfileEntity getUserById(String userId);

    List<UserProfileEntity> getUsers(List<String> userIds);
}
