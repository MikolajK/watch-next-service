package dev.mikolajk.watchnext.omdb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@With
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class OmdbWatchableSearchRepresentation {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    // Only IMDB ID to determine equality - two films can't have the same ID
    @EqualsAndHashCode.Include
    @JsonProperty("imdbID")
    private String imdbId;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Poster")
    private String poster;

}
