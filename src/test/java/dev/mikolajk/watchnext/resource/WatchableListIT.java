package dev.mikolajk.watchnext.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import dev.mikolajk.watchnext.omdb.model.DetailedOmdbWatchableRepresentation;
import dev.mikolajk.watchnext.resource.model.AddWatchablesToListRequestBody;
import dev.mikolajk.watchnext.resource.model.CreateListRequestBody;
import dev.mikolajk.watchnext.service.model.list.DetailedWatchableListRepresentation;
import dev.mikolajk.watchnext.service.model.list.SimpleWatchableListRepresentation;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
@DisplayName("Watchable List API")
class WatchableListIT extends TestBase {

    @Nested
    @DisplayName("Create List")
    class WatchableCreateListIT {

        @Test
        @DisplayName("Valid Name")
        void createList_validName_returns200() {
            String listName = "Watching With Monica";
            CreateListRequestBody createListRequestBody = new CreateListRequestBody(listName);

            SimpleWatchableListRepresentation returnedList = authenticatedRequest()
                .body(createListRequestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/watchable/list")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(SimpleWatchableListRepresentation.class);

            assertThat(returnedList.getId()).isPositive();
            assertThat(returnedList.getName()).isEqualTo(listName);

            List<SimpleWatchableListRepresentation> existingLists =
                authenticatedRequest()
                    .get("/watchable/list")
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().jsonPath().getList("", SimpleWatchableListRepresentation.class);

            SimpleWatchableListRepresentation lastList = existingLists.get(existingLists.size() - 1);
            assertThat(lastList.getId()).isEqualTo(returnedList.getId());
            assertThat(lastList.getName()).isEqualTo(returnedList.getName());

            DetailedWatchableListRepresentation detailedList = authenticatedRequest()
                .get("/watchable/list/{id}", returnedList.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(DetailedWatchableListRepresentation.class);

            assertThat(detailedList.getId()).isEqualTo(returnedList.getId());
            assertThat(detailedList.getName()).isEqualTo(returnedList.getName());
            assertThat(detailedList.getWatchables()).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Invalid Name - Too Long")
        void createList_oversizedName_returns400() {
            String listName = RandomStringUtils.randomAlphabetic(65);
            CreateListRequestBody createListRequestBody = new CreateListRequestBody(listName);

            authenticatedRequest()
                .body(createListRequestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/watchable/list")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("parameterViolations[0].message", equalTo("size must be between 1 and 64"));
        }

        @Test
        @DisplayName("Invalid Name - Too Short")
        void createList_emptyName_returns400() {
            String listName = "";
            CreateListRequestBody createListRequestBody = new CreateListRequestBody(listName);

            authenticatedRequest()
                .body(createListRequestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/watchable/list")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("parameterViolations[0].message", equalTo("size must be between 1 and 64"));
        }

    }

    @Nested
    @DisplayName("Add Watchables To List")
    class AddWatchableToListIT {

        @Test
        @DisplayName("Existing Watchables")
        void addWatchablesToList_existingWatchables_returnsNoContent() {
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
                .post("/watchable/list/{listId}/watchable", listId)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

            DetailedWatchableListRepresentation list =
                authenticatedRequest()
                    .get("/watchable/list/{listId}", listId)
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().body().as(DetailedWatchableListRepresentation.class);

            assertThat(list.getWatchables()).hasSize(1);
            assertThat(list.getWatchables().get(0).getImdbId()).isEqualTo(watchableId);
        }
    }

}
