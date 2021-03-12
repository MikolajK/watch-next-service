package dev.mikolajk.watchnext.resource.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordUserVoteRequestBody {

    @Min(0)
    @Max(100)
    private int votes;

}
