package dev.mikolajk.watchnext.service;

import dev.mikolajk.watchnext.service.model.list.DetailedWatchableListRepresentation;
import dev.mikolajk.watchnext.service.model.list.SimpleWatchableListRepresentation;
import dev.mikolajk.watchnext.service.model.search.WatchableSearchResults;
import dev.mikolajk.watchnext.service.model.user.UserProfile;
import java.util.List;

public interface WatchableService {

    WatchableSearchResults search(String query, int pageNumber);

    SimpleWatchableListRepresentation createList(String listName);

    List<SimpleWatchableListRepresentation> getLists();

    DetailedWatchableListRepresentation getList(long listId);

    void addToList(long listId, List<String> imdbIds);

    void storeUserVote(long listId, String watchableId, int vote);

    List<UserProfile> getAssignedUsers(long listId);

    void assignUser(long listId, String username);

    void unassignUser(long listId, String username);
}
