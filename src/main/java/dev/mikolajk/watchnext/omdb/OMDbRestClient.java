package dev.mikolajk.watchnext.omdb;

import dev.mikolajk.watchnext.omdb.model.DetailedOmdbWatchableRepresentation;
import dev.mikolajk.watchnext.omdb.model.OmdbSearchResult;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

public interface OMDbRestClient {

    @GET
    @Path("/")
    OmdbSearchResult searchByTitle(
        @QueryParam("s") String title,
        @QueryParam("apiKey") String apiKey,
        @QueryParam("page") @DefaultValue("1") int pageNumber
    );

    @GET
    @Path("/")
    DetailedOmdbWatchableRepresentation searchByImdbId(@QueryParam("i") String imdbId,
        @QueryParam("apiKey") String apiKey);
}
