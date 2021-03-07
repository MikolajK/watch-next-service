package dev.mikolajk.watchnext.omdb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OmdbRating {

    @JsonProperty("Source")
    private String source;

    @JsonProperty("Value")
    private String value;

}
