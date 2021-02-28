package dev.mikolajk.watchnext.service;

import dev.mikolajk.watchnext.model.WatchableSearchResults;

public interface WatchableService {

    WatchableSearchResults search(String query, int pageNumber);
}
