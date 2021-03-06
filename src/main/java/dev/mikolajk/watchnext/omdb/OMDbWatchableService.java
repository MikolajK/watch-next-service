package dev.mikolajk.watchnext.omdb;

import static dev.mikolajk.watchnext.service.errors.ApiErrorMessages.LIST_NOT_FOUND;
import static dev.mikolajk.watchnext.service.errors.ApiErrorMessages.USER_NOT_FOUND_IN_DATABASE;

import dev.mikolajk.watchnext.omdb.model.OmdbSearchResult;
import dev.mikolajk.watchnext.persistence.UserProfileRepository;
import dev.mikolajk.watchnext.persistence.WatchableRepository;
import dev.mikolajk.watchnext.persistence.mapper.JpaMapper;
import dev.mikolajk.watchnext.persistence.model.list.UserListAssignmentEntity;
import dev.mikolajk.watchnext.persistence.model.list.WatchableListEntity;
import dev.mikolajk.watchnext.persistence.model.user.UserProfileEntity;
import dev.mikolajk.watchnext.service.WatchableService;
import dev.mikolajk.watchnext.service.model.list.DetailedWatchableListRepresentation;
import dev.mikolajk.watchnext.service.model.list.SimpleWatchableListRepresentation;
import dev.mikolajk.watchnext.service.model.search.WatchableSearchResults;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
@Transactional
public class OMDbWatchableService implements WatchableService {

    private final OMDbRestClient omDbRestClient;
    private final OMDbConfiguration omDbConfiguration;
    private final OmdbSearchResultMapper omdbSearchResultMapper;
    private final WatchableRepository watchableRepository;
    private final UserProfileRepository userProfileRepository;
    private final JpaMapper jpaMapper;

    @Inject
    public OMDbWatchableService(OMDbRestClient omDbRestClient, OMDbConfiguration omDbConfiguration,
        OmdbSearchResultMapper omdbSearchResultMapper, WatchableRepository watchableRepository,
        UserProfileRepository userProfileRepository, JpaMapper jpaMapper) {
        this.omDbRestClient = omDbRestClient;
        this.omDbConfiguration = omDbConfiguration;
        this.omdbSearchResultMapper = omdbSearchResultMapper;
        this.watchableRepository = watchableRepository;
        this.userProfileRepository = userProfileRepository;
        this.jpaMapper = jpaMapper;
    }

    @Override
    public WatchableSearchResults search(String query, int pageNumber) {
        OmdbSearchResult omdbSearchResult = omDbRestClient
            .searchByTitle(query, omDbConfiguration.getApiKey(), pageNumber);

        return omdbSearchResultMapper.toWatchableSearchResults(omdbSearchResult);
    }

    @Override
    public SimpleWatchableListRepresentation createList(String listName) {
        UserProfileEntity user = userProfileRepository.getUserById("dummy");
        if (user == null) {
            throw new InternalServerErrorException(USER_NOT_FOUND_IN_DATABASE);
        }

        WatchableListEntity list = new WatchableListEntity();
        list.setName(listName);
        watchableRepository.createNewList(list);

        UserListAssignmentEntity watchableListAssignmentEntity = new UserListAssignmentEntity();
        watchableListAssignmentEntity.setListId(list.getListId());
        watchableListAssignmentEntity.setUserId("dummy");
        list.setUsers(Collections.singletonList(watchableListAssignmentEntity));

        watchableRepository.saveUserListAssignment(watchableListAssignmentEntity);

        return jpaMapper.toSimpleWatchableListRepresentation(list);
    }

    @Override
    public List<SimpleWatchableListRepresentation> getLists() {
        List<WatchableListEntity> listEntities = watchableRepository.getLists("dummy");

        return jpaMapper.toSimpleWatchableListRepresentationList(listEntities);
    }

    @Override
    public DetailedWatchableListRepresentation getList(long listId) {
        WatchableListEntity list = watchableRepository.getList("dummy", listId);
        if (list == null) {
            throw new NotFoundException(LIST_NOT_FOUND);
        }
        return jpaMapper.toDetailedWatchableListRepresentation(list);
    }

}
