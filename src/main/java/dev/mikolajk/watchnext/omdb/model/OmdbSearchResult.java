package dev.mikolajk.watchnext.omdb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@With
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OmdbSearchResult {

    @JsonProperty("Search")
    private List<OmdbWatchableSearchRepresentation> search;

    @JsonProperty("totalResults")
    private int totalResults;

    @JsonProperty("Response")
    private boolean response;

}

