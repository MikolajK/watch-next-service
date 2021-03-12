package dev.mikolajk.watchnext.service.model.watchable;

import dev.mikolajk.watchnext.service.model.user.UserProfile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVoteRepresentation {

    private UserProfile user;
    private int votes;

}
