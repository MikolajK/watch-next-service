package dev.mikolajk.watchnext.omdb;

import dev.mikolajk.watchnext.omdb.model.DetailedOmdbWatchableRepresentation;
import dev.mikolajk.watchnext.omdb.model.OmdbSearchResult;
import dev.mikolajk.watchnext.omdb.model.SimpleOmdbWatchableRepresentation;
import dev.mikolajk.watchnext.persistence.model.list.WatchableEntity;
import dev.mikolajk.watchnext.service.model.search.WatchableSearchResults;
import dev.mikolajk.watchnext.service.model.watchable.SimpleWatchableRepresentation;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface OmdbSearchResultMapper {

    @Mapping(target = "posterUrl", source = "poster")
    SimpleWatchableRepresentation toWatchableSearchRepresentation(SimpleOmdbWatchableRepresentation omdbWatchable);

    default WatchableSearchResults toWatchableSearchResults(OmdbSearchResult omdbSearchResult) {
        int totalResults = omdbSearchResult.getTotalResults();

        WatchableSearchResults watchableSearchResults = new WatchableSearchResults();
        watchableSearchResults.setTotalResults(totalResults);

        // 10 is the page size in OMDb
        watchableSearchResults.setTotalPages((totalResults / 10) + Math.min(1, totalResults % 10));

        watchableSearchResults.setResults(
            Optional.ofNullable(omdbSearchResult.getSearch()).orElse(new ArrayList<>())
                .stream()
                .map(this::toWatchableSearchRepresentation)
                .collect(Collectors.toList())
        );

        return watchableSearchResults;
    }

    @Mapping(source = "plot", target = "plotSummary")
    @Mapping(source = "poster", target = "posterUrl")
    WatchableEntity toWatchableEntity(DetailedOmdbWatchableRepresentation watchable);
}
