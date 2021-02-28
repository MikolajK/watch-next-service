package dev.mikolajk.watchnext.resource;

import dev.mikolajk.watchnext.model.WatchableSearchResults;
import dev.mikolajk.watchnext.service.WatchableService;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/watchable")
public class WatchableResource {

    private final WatchableService watchableService;

    @Inject
    public WatchableResource(WatchableService watchableService) {
        this.watchableService = watchableService;
    }

    @POST
    @Path("/search")
    @Produces({MediaType.APPLICATION_JSON})
    public WatchableSearchResults search(
        @NotNull @Size(min = 1, max = 64) @QueryParam("query") String query,
        @QueryParam("page") @DefaultValue("1") int pageNumber) {
        return watchableService.search(query, pageNumber);
    }

}
