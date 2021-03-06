package dev.mikolajk.watchnext.resource;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import dev.mikolajk.watchnext.persistence.WatchableRepository;
import dev.mikolajk.watchnext.resource.model.CreateListRequestBody;
import dev.mikolajk.watchnext.service.model.list.SimpleWatchableListRepresentation;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import javax.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@QuarkusTest
@DisplayName("Watchable List API")
class WatchableListIT {

    @Inject
    private WatchableRepository repository;

    @Nested
    @DisplayName("Create List")
    class WatchableSearchByTitleIT {

        @Test
        @DisplayName("Valid Name")
        void createList_validName_returns200() {
            String listName = "Watching With Monica";
            CreateListRequestBody createListRequestBody = new CreateListRequestBody(listName);

            SimpleWatchableListRepresentation returnedList = given()
                .body(createListRequestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/watchable/list")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(SimpleWatchableListRepresentation.class);

            assertThat(returnedList.getId()).isNotNull();
            assertThat(returnedList.getName()).isEqualTo(listName);
        }

        @Test
        @DisplayName("Invalid Name - Too Long")
        void createList_oversizedName_returns400() {
            String listName = RandomStringUtils.randomAlphabetic(65);
            CreateListRequestBody createListRequestBody = new CreateListRequestBody(listName);

            given()
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

            given()
                .body(createListRequestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/watchable/list")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("parameterViolations[0].message", equalTo("size must be between 1 and 64"));
        }

    }

}
