package dev.mikolajk.watchnext.resource;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import dev.mikolajk.watchnext.omdb.model.OmdbSearchResult;
import dev.mikolajk.watchnext.omdb.model.SimpleOmdbWatchableRepresentation;
import dev.mikolajk.watchnext.service.model.search.WatchableSearchResults;
import dev.mikolajk.watchnext.service.model.watchable.SimpleWatchableRepresentation;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

@DisplayName("Watchable Search API")
@QuarkusTest
class WatchableSearchApiIT extends OmdbApiTestBase {

    private static final String BASE_PATH = "/watchable/search";
    private static final String POSTERS_URL = "http://posters.fakesite/";
    private static final String TYPE = "movie";
    private static final String IMDB_ID_PREFIX = "imdbid:";

    @Nested
    @DisplayName("Search By Title")
    class WatchableSearchByTitleIT {

        @DisplayName("Default Page Number And Acceptable Title")
        @Test
        void searchByTitle_defaultPageNumberAcceptableTitle_returnsResults() {
            OmdbSearchResult expectedResults = new OmdbSearchResult()
                .withTotalResults(2)
                .withResponse(true)
                .withSearch(
                    Arrays.asList(
                        omdbWatchableSearchResult("Knives Out", "2019"),
                        omdbWatchableSearchResult("Knives Originals", "2003")
                    )
                );

            String queryValue = "Knives+O";
            BDDMockito
                .given(omdbRestClientMock.searchByTitle(queryValue, API_KEY, 1)).willReturn(expectedResults);

            WatchableSearchResults results = given()
                .queryParam("query", queryValue)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body().as(WatchableSearchResults.class);

            assertThat(results.getTotalPages()).isOne();
            assertThat(results.getTotalResults()).isEqualTo(2);
            List<SimpleWatchableRepresentation> watchableRepresentations = results.getResults();

            SimpleWatchableRepresentation knivesOut = watchableRepresentations.get(0);
            verifySearchResult(knivesOut, "Knives Out", "2019");

            SimpleWatchableRepresentation knivesOriginals = watchableRepresentations.get(1);
            verifySearchResult(knivesOriginals, "Knives Originals", "2003");
        }

        private void verifySearchResult(SimpleWatchableRepresentation result, String title, String year) {
            assertThat(result.getTitle()).isEqualTo(title);
            assertThat(result.getYear()).isEqualTo(year);
            assertThat(result.getImdbId()).isEqualTo(IMDB_ID_PREFIX + title);
            assertThat(result.getPosterUrl()).isEqualTo(POSTERS_URL + title);
        }
    }

    private SimpleOmdbWatchableRepresentation omdbWatchableSearchResult(String title, String year) {
        return new SimpleOmdbWatchableRepresentation()
            .withTitle(title)
            .withYear(year)
            .withPoster(POSTERS_URL + title)
            .withImdbId(IMDB_ID_PREFIX + title)
            .withType(TYPE);
    }

}