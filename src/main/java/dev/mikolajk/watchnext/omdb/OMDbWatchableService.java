package dev.mikolajk.watchnext.omdb;

import static dev.mikolajk.watchnext.service.errors.ApiErrorMessages.LIST_NOT_FOUND;
import static dev.mikolajk.watchnext.service.errors.ApiErrorMessages.USER_NOT_FOUND_IN_DATABASE;

import dev.mikolajk.watchnext.omdb.model.DetailedOmdbWatchableRepresentation;
import dev.mikolajk.watchnext.omdb.model.OmdbSearchResult;
import dev.mikolajk.watchnext.persistence.UserProfileRepository;
import dev.mikolajk.watchnext.persistence.WatchableRepository;
import dev.mikolajk.watchnext.persistence.mapper.JpaMapper;
import dev.mikolajk.watchnext.persistence.model.list.UserListAssignmentEntity;
import dev.mikolajk.watchnext.persistence.model.list.WatchableEntity;
import dev.mikolajk.watchnext.persistence.model.list.WatchableListAssignmentEntity;
import dev.mikolajk.watchnext.persistence.model.list.WatchableListEntity;
import dev.mikolajk.watchnext.persistence.model.user.UserProfileEntity;
import dev.mikolajk.watchnext.service.WatchableService;
import dev.mikolajk.watchnext.service.model.list.DetailedWatchableListRepresentation;
import dev.mikolajk.watchnext.service.model.list.SimpleWatchableListRepresentation;
import dev.mikolajk.watchnext.service.model.search.WatchableSearchResults;
import dev.mikolajk.watchnext.service.model.watchable.DetailedWatchableRepresentation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
        watchableRepository.saveList(list);

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
        WatchableListEntity list = getListEntityOrReturnNotFound(listId);
        DetailedWatchableListRepresentation detailedWatchableListRepresentation = jpaMapper
            .toDetailedWatchableListRepresentation(list);

        List<String> watchableIds = list.getWatchables().stream()
            .map(WatchableListAssignmentEntity::getWatchableId).collect(Collectors.toList());

        List<WatchableEntity> watchablesByIds = watchableRepository.getWatchablesByIds(watchableIds);

        List<DetailedWatchableRepresentation> watchables = jpaMapper
            .toDetailedWatchableRepresentationList(watchablesByIds);

        detailedWatchableListRepresentation.setWatchables(watchables);
        return detailedWatchableListRepresentation;
    }

    @Override
    public void addToList(long listId, List<String> imdbIds) {
        getListEntityOrReturnNotFound(listId);

        List<String> existingWatchablesIds = watchableRepository.getWatchablesByIds(imdbIds)
            .stream()
            .map(WatchableEntity::getImdbId)
            .collect(Collectors.toList());

        List<String> missingWatchableIds = new ArrayList<>(imdbIds);
        missingWatchableIds.removeIf(existingWatchablesIds::contains);
        missingWatchableIds.forEach(this::storeWatchableData);

        imdbIds.forEach(id -> storeWatchableListAssignment(listId, id));
    }

    private void storeWatchableListAssignment(long listId, String id) {
        WatchableListAssignmentEntity watchableListAssignmentEntity = new WatchableListAssignmentEntity();
        watchableListAssignmentEntity.setListId(listId);
        watchableListAssignmentEntity.setWatchableId(id);

        watchableRepository.saveWatchableListAssignment(watchableListAssignmentEntity);
    }

    private void storeWatchableData(String imdbId) {
        DetailedOmdbWatchableRepresentation watchable = omDbRestClient
            .searchByImdbId(imdbId, omDbConfiguration.getApiKey());

        WatchableEntity watchableEntity = omdbSearchResultMapper.toWatchableEntity(watchable);
        watchableRepository.saveWatchable(watchableEntity);
    }

    private WatchableListEntity getListEntityOrReturnNotFound(long listId) {
        return watchableRepository.getList("dummy", listId)
            .orElseThrow(() -> new NotFoundException(LIST_NOT_FOUND));
    }
}
