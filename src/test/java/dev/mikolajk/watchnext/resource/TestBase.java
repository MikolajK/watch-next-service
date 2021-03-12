package dev.mikolajk.watchnext.resource;

import dev.mikolajk.watchnext.MariaDbContainerResource;
import dev.mikolajk.watchnext.omdb.OMDbRestClient;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusMock;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

@QuarkusTestResource(MariaDbContainerResource.class)
class TestBase {

    static final String API_KEY = "test-api-key";
    static OMDbRestClient omdbRestClientMock;

    @BeforeAll
    static void setup() {
        omdbRestClientMock = Mockito.mock(OMDbRestClient.class);
        QuarkusMock.installMockForType(omdbRestClientMock, OMDbRestClient.class);
    }

}
