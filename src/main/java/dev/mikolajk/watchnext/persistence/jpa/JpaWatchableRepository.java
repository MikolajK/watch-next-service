package dev.mikolajk.watchnext.persistence.jpa;

import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_LISTS_FOR_USER_QUERY_NAME;
import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_LIST_BY_ID_AND_USER_ID_NAME;

import dev.mikolajk.watchnext.persistence.WatchableRepository;
import dev.mikolajk.watchnext.persistence.model.list.UserListAssignmentEntity;
import dev.mikolajk.watchnext.persistence.model.list.WatchableListEntity;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@ApplicationScoped
public class JpaWatchableRepository implements WatchableRepository {

    private final EntityManager entityManager;

    @Inject
    public JpaWatchableRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createNewList(WatchableListEntity list) {
        entityManager.persist(list);
    }

    @Override
    public List<WatchableListEntity> getLists(String userId) {
        return entityManager
            .createNamedQuery(GET_LISTS_FOR_USER_QUERY_NAME, WatchableListEntity.class)
            .setParameter("userId", userId)
            .getResultList();
    }

    @Override
    public WatchableListEntity getList(String userId, long listId) {
        try {
            return entityManager
                .createNamedQuery(GET_LIST_BY_ID_AND_USER_ID_NAME, WatchableListEntity.class)
                .setParameter("userId", userId)
                .setParameter("listId", listId)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    @Override
    public void saveUserListAssignment(UserListAssignmentEntity watchableListAssignmentEntity) {
        entityManager.persist(watchableListAssignmentEntity);
    }
}
