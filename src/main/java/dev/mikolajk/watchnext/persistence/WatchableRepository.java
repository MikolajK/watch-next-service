package dev.mikolajk.watchnext.persistence;

import dev.mikolajk.watchnext.persistence.model.list.UserListAssignmentEntity;
import dev.mikolajk.watchnext.persistence.model.list.WatchableListEntity;
import java.util.List;

public interface WatchableRepository {

    void createNewList(WatchableListEntity list);

    List<WatchableListEntity> getLists(String userId);

    WatchableListEntity getList(String userId, long listId);

    void saveUserListAssignment(UserListAssignmentEntity watchableListAssignmentEntity);
}
