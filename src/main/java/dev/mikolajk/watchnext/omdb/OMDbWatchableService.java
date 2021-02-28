package dev.mikolajk.watchnext.omdb;

import dev.mikolajk.watchnext.model.WatchableSearchResults;
import dev.mikolajk.watchnext.omdb.model.OmdbSearchResult;
import dev.mikolajk.watchnext.service.WatchableService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class OMDbWatchableService implements WatchableService {

    private final OMDbRestClient omDbRestClient;
    private final OMDbConfiguration omDbConfiguration;
    private final OmdbSearchResultMapper omdbSearchResultMapper;

    @Inject
    public OMDbWatchableService(OMDbRestClient omDbRestClient, OMDbConfiguration omDbConfiguration,
        OmdbSearchResultMapper omdbSearchResultMapper) {
        this.omDbRestClient = omDbRestClient;
        this.omDbConfiguration = omDbConfiguration;
        this.omdbSearchResultMapper = omdbSearchResultMapper;
    }

    public WatchableSearchResults search(String query, int pageNumber) {
        OmdbSearchResult omdbSearchResult = omDbRestClient
            .searchByTitle(query, omDbConfiguration.getApiKey(), pageNumber);

        return omdbSearchResultMapper.toWatchableSearchResults(omdbSearchResult);
    }
}
