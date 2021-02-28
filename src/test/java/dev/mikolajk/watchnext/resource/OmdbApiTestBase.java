package dev.mikolajk.watchnext.resource;

import dev.mikolajk.watchnext.omdb.OMDbRestClient;
import io.quarkus.test.junit.QuarkusMock;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

class OmdbApiTestBase {

    static OMDbRestClient omdbRestClientMock;

    @BeforeAll
    static void setup() {
        omdbRestClientMock = Mockito.mock(OMDbRestClient.class);
        QuarkusMock.installMockForType(omdbRestClientMock, OMDbRestClient.class);
    }

}
