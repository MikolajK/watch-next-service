package dev.mikolajk.watchnext.persistence.model.id;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserIdAndListIdCompositeKey implements Serializable {

    private String userId;
    private long listId;

}
