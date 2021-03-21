package dev.mikolajk.watchnext.resource;

import dev.mikolajk.watchnext.omdb.model.DetailedOmdbWatchableRepresentation;
import dev.mikolajk.watchnext.resource.model.AddWatchablesToListRequestBody;
import dev.mikolajk.watchnext.resource.model.CreateListRequestBody;
import dev.mikolajk.watchnext.resource.model.RecordUserVoteRequestBody;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.util.Collections;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.mockito.Mockito;

@QuarkusTest
@DisplayName("Watchable Vote API")
public class WatchableVoteApiIT extends TestBase {

    @Nested
    @DisplayName("Record User Vote")
    class RecordUserVoteApiIT {

        @DisplayName("Valid Vote")
        void recordUserVote_validVote_returnsNoContent() {
            String watchableId = "tt0106364";
            DetailedOmdbWatchableRepresentation watchableRepresentation = new DetailedOmdbWatchableRepresentation();
            watchableRepresentation.setTitle("Test Watchable");
            watchableRepresentation.setImdbId(watchableId);

            Mockito.when(omdbRestClientMock.searchByImdbId(watchableId, API_KEY))
                .thenReturn(watchableRepresentation);

            CreateListRequestBody createListRequestBody = new CreateListRequestBody();
            createListRequestBody.setName("Test List");

            long listId = authenticatedRequest()
                .body(createListRequestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/watchable/list")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().jsonPath().getLong("id");

            List<String> watchableIds = Collections.singletonList(watchableId);
            AddWatchablesToListRequestBody requestBody = new AddWatchablesToListRequestBody();
            requestBody.setImdbIds(watchableIds);

            authenticatedRequest()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/watchable/list/{listId}/watchables", listId)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

            RecordUserVoteRequestBody voteRequestBody = new RecordUserVoteRequestBody();
            voteRequestBody.setVotes(5);

            authenticatedRequest()
                .body(voteRequestBody)
                .contentType(ContentType.JSON)
                .when()
                .put("/watchable/list/{listId}/watchables/{watchableId}/vote", listId, watchableId)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        }
    }
}
