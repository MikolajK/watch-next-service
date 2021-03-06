package dev.mikolajk.watchnext.persistence.model.id;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WatchableAndListIdCompositeKey implements Serializable {

    private String watchableId;
    private int listId;

}
