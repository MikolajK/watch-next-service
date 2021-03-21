package dev.mikolajk.watchnext.persistence.jpa;

import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_LISTS_FOR_USER_QUERY_NAME;
import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_LIST_BY_ID_AND_USER_ID_NAME;
import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_USERS_FOR_LIST_ID_NAME;
import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_USERS_VOTES_FOR_WATCHABLE_AND_LIST_NAME;
import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_WATCHABLES_BY_LIST_OF_IDS_NAME;

import dev.mikolajk.watchnext.persistence.WatchableRepository;
import dev.mikolajk.watchnext.persistence.model.list.UserListAssignmentEntity;
import dev.mikolajk.watchnext.persistence.model.list.UserWatchableVoteEntity;
import dev.mikolajk.watchnext.persistence.model.list.WatchableEntity;
import dev.mikolajk.watchnext.persistence.model.list.WatchableListAssignmentEntity;
import dev.mikolajk.watchnext.persistence.model.list.WatchableListEntity;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import org.hibernate.Session;

@ApplicationScoped
@Transactional
public class JpaWatchableRepository implements WatchableRepository {

    private final EntityManager entityManager;

    @Inject
    public JpaWatchableRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveList(WatchableListEntity list) {
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
    public Optional<WatchableListEntity> getList(String userId, long listId) {
        try {
            return Optional.of(entityManager
                .createNamedQuery(GET_LIST_BY_ID_AND_USER_ID_NAME, WatchableListEntity.class)
                .setParameter("userId", userId)
                .setParameter("listId", listId)
                .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    @Override
    public void saveUserListAssignment(UserListAssignmentEntity watchableListAssignmentEntity) {
        entityManager.persist(watchableListAssignmentEntity);
    }

    @Override
    public List<WatchableEntity> getWatchablesByIds(List<String> ids) {
        return entityManager
            .createNamedQuery(GET_WATCHABLES_BY_LIST_OF_IDS_NAME, WatchableEntity.class)
            .setParameter("ids", ids)
            .getResultList();
    }

    @Override
    public void saveWatchable(WatchableEntity watchableEntity) {
        entityManager.persist(watchableEntity);
    }

    @Override
    public void saveWatchableListAssignment(WatchableListAssignmentEntity watchableListAssignmentEntity) {
        entityManager.persist(watchableListAssignmentEntity);
    }

    @Override
    public void saveUserVote(UserWatchableVoteEntity voteEntity) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(voteEntity);
    }

    @Override
    public List<UserWatchableVoteEntity> getUserVotesForWatchableAndList(long listId, String watchableId) {
        return entityManager
            .createNamedQuery(GET_USERS_VOTES_FOR_WATCHABLE_AND_LIST_NAME, UserWatchableVoteEntity.class)
            .setParameter("listId", listId)
            .setParameter("watchableId", watchableId)
            .getResultList();
    }

    @Override
    public List<UserListAssignmentEntity> getUsersForList(long listId) {
        return entityManager
            .createNamedQuery(GET_USERS_FOR_LIST_ID_NAME, UserListAssignmentEntity.class)
            .setParameter("listId", listId)
            .getResultList();
    }
}
