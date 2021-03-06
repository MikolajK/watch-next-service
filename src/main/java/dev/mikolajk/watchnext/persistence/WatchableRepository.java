package dev.mikolajk.watchnext.persistence;

import dev.mikolajk.watchnext.persistence.model.id.UserIdAndListIdCompositeKey;
import dev.mikolajk.watchnext.persistence.model.list.UserListAssignmentEntity;
import dev.mikolajk.watchnext.persistence.model.list.UserWatchableVoteEntity;
import dev.mikolajk.watchnext.persistence.model.list.WatchableEntity;
import dev.mikolajk.watchnext.persistence.model.list.WatchableListAssignmentEntity;
import dev.mikolajk.watchnext.persistence.model.list.WatchableListEntity;
import java.util.List;
import java.util.Optional;

public interface WatchableRepository {

    void saveList(WatchableListEntity list);

    List<WatchableListEntity> getLists(String userId);

    Optional<WatchableListEntity> getList(String userId, long listId);

    void saveUserListAssignment(UserListAssignmentEntity watchableListAssignmentEntity);

    List<WatchableEntity> getWatchablesByIds(List<String> ids);

    void saveWatchable(WatchableEntity watchableEntity);

    void saveWatchableListAssignment(WatchableListAssignmentEntity watchableListAssignmentEntity);

    void saveUserVote(UserWatchableVoteEntity voteEntity);

    List<UserWatchableVoteEntity> getUserVotesForWatchableAndList(long listId, String watchableId);

    List<UserListAssignmentEntity> getUsersForList(long listId);

    void deleteUserListAssignment(UserIdAndListIdCompositeKey userListAssignmentEntity);
}
