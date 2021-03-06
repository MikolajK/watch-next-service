package dev.mikolajk.watchnext.resource.model;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateListRequestBody {

    @Size(min = 1, max = 64)
    private String name;

}
