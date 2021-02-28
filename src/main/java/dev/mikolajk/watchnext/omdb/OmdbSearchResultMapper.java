package dev.mikolajk.watchnext.omdb;

import dev.mikolajk.watchnext.model.WatchableSearchRepresentation;
import dev.mikolajk.watchnext.model.WatchableSearchResults;
import dev.mikolajk.watchnext.omdb.model.OmdbSearchResult;
import dev.mikolajk.watchnext.omdb.model.OmdbWatchableSearchRepresentation;
import java.util.Comparator;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface OmdbSearchResultMapper {

    @Mapping(target = "posterUrl", source = "poster")
    WatchableSearchRepresentation toWatchableSearchRepresentation(OmdbWatchableSearchRepresentation omdbWatchable);

    default WatchableSearchResults toWatchableSearchResults(OmdbSearchResult omdbSearchResult) {
        int totalResults = omdbSearchResult.getTotalResults();

        WatchableSearchResults watchableSearchResults = new WatchableSearchResults();
        watchableSearchResults.setTotalResults(totalResults);

        // 10 is the page size in OMDb
        watchableSearchResults.setTotalPages((totalResults / 10) + Math.min(1, totalResults % 10));

        watchableSearchResults.setResults(
            omdbSearchResult.getSearch()
                .stream()
                .map(this::toWatchableSearchRepresentation)
                .collect(Collectors.toList())
        );

        return watchableSearchResults;
    }

}
