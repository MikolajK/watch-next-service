package dev.mikolajk.watchnext.service.model.search;

import dev.mikolajk.watchnext.service.model.watchable.SimpleWatchableRepresentation;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WatchableSearchResults {

    private int totalResults;
    private int totalPages;
    private List<SimpleWatchableRepresentation> results;

}
