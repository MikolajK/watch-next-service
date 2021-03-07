package dev.mikolajk.watchnext.resource.model;

import java.util.List;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddWatchablesToListRequestBody {

    @Size(min = 1, max = 32)
    private List<String> imdbIds;

}
