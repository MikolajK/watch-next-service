package dev.mikolajk.watchnext.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WatchableSearchResults {

    private int totalResults;
    private int totalPages;
    private List<WatchableSearchRepresentation> results;

}
