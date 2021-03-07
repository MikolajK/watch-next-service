package dev.mikolajk.watchnext.service.model.watchable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailedWatchableRepresentation extends SimpleWatchableRepresentation {

    private String genre;
    private String plotSummary;
    private String runtime;

}

