package dev.mikolajk.watchnext.resource;

import io.quarkus.security.identity.SecurityIdentity;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces(value = {"application/json"})
public class UserResource {

    @Inject
    private SecurityIdentity securityIdentity;

    @GET
    @Path("/me")
    public Response getCurrentUserInfo() {
        return Response.ok(Map.of("name", securityIdentity.getPrincipal().getName())).build();
    }

}
