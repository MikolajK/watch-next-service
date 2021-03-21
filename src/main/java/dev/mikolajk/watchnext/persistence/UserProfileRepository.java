package dev.mikolajk.watchnext.persistence;

import dev.mikolajk.watchnext.service.model.user.UserProfile;
import java.util.List;

public interface UserProfileRepository {

    UserProfile getUserById(String userId);

    List<UserProfile> getUsers(List<String> userIds);
}
