package dev.mikolajk.watchnext.resource;

import dev.mikolajk.watchnext.resource.model.AddWatchablesToListRequestBody;
import dev.mikolajk.watchnext.resource.model.CreateListRequestBody;
import dev.mikolajk.watchnext.resource.model.RecordUserVoteRequestBody;
import dev.mikolajk.watchnext.service.WatchableService;
import dev.mikolajk.watchnext.service.model.list.DetailedWatchableListRepresentation;
import dev.mikolajk.watchnext.service.model.list.SimpleWatchableListRepresentation;
import dev.mikolajk.watchnext.service.model.user.UserProfile;
import io.quarkus.security.Authenticated;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/watchable")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class WatchableResource {

    private final WatchableService watchableService;

    @Inject
    public WatchableResource(WatchableService watchableService) {
        this.watchableService = watchableService;
    }

    @POST
    @Path("/search")
    public Response search(
        @NotNull @Size(min = 1, max = 64) @QueryParam("query") String query,
        @QueryParam("page") @DefaultValue("1") int pageNumber) {
        return Response.ok(watchableService.search(query, pageNumber)).build();
    }

    @POST
    @Path("/list")
    public Response createList(@Valid CreateListRequestBody requestBody) {
        SimpleWatchableListRepresentation list = watchableService.createList(requestBody.getName());
        return Response.ok(list).build();
    }

    @GET
    @Path("/list")
    public Response getLists() {
        List<SimpleWatchableListRepresentation> watchableLists = watchableService.getLists();

        return Response.ok(watchableLists).build();
    }

    @GET
    @Path("/list/{id}")
    public Response getList(@PathParam("id") long listId) {
        DetailedWatchableListRepresentation list = watchableService.getList(listId);
        return Response.ok(list).build();
    }

    @POST
    @Path("/list/{id}/watchable")
    public Response addToList(@PathParam("id") long listId,
        @Valid AddWatchablesToListRequestBody requestBody) {
        watchableService.addToList(listId, requestBody.getImdbIds());

        return Response.noContent().build();
    }

    @PUT
    @Path("/list/{listId}/watchable/{watchableId}/vote")
    public Response recordUserVote(
        @PathParam("listId") long listId,
        @PathParam("watchableId") String watchableId,
        @Valid RecordUserVoteRequestBody requestBody) {
        watchableService.storeUserVote(listId, watchableId, requestBody.getVotes());
        return Response.noContent().build();
    }

    @GET
    @Path("/list/{listId}/user")
    public Response getAssignedUsers(@PathParam("listId") long listId) {
        List<UserProfile> assignedUsers = watchableService.getAssignedUsers(listId);
        return Response.ok(assignedUsers).build();
    }

}
