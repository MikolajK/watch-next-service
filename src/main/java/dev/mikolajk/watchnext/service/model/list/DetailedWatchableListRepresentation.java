package dev.mikolajk.watchnext.service.model.list;

import dev.mikolajk.watchnext.service.model.watchable.DetailedWatchableRepresentation;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailedWatchableListRepresentation extends SimpleWatchableListRepresentation {

    private List<DetailedWatchableRepresentation> watchables = new ArrayList<>();

}
