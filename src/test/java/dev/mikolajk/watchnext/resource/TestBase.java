package dev.mikolajk.watchnext.resource;

import static io.restassured.RestAssured.given;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.mikolajk.watchnext.MariaDbContainerResource;
import dev.mikolajk.watchnext.MockAuthorizationServer;
import dev.mikolajk.watchnext.omdb.OMDbRestClient;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusMock;
import io.restassured.specification.RequestSpecification;
import java.util.Collections;
import java.util.Date;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

@QuarkusTestResource(MariaDbContainerResource.class)
@QuarkusTestResource(MockAuthorizationServer.class)
class TestBase {

    static final String API_KEY = "test-api-key";
    static OMDbRestClient omdbRestClientMock;

    @BeforeAll
    static void setup() {
        omdbRestClientMock = Mockito.mock(OMDbRestClient.class);
        QuarkusMock.installMockForType(omdbRestClientMock, OMDbRestClient.class);
    }

    RequestSpecification authenticatedRequest() {
        return given()
            .header("Authorization", "Bearer " + generateJWT());
    }


    private String generateJWT() {
        // Prepare JWT with claims set
        SignedJWT signedJWT = new SignedJWT(
            new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(MockAuthorizationServer.RSA_KEY.getKeyID())
                .type(JOSEObjectType.JWT)
                .build(),
            new JWTClaimsSet.Builder()
                .subject("backend-service")
                .issuer("https://wiremock")
                .claim(
                    "realm_access",
                    new JWTClaimsSet.Builder()
                        .claim("roles", Collections.singletonList("user"))
                        .build()
                        .toJSONObject()
                )
                .claim("scope", "openid email profile")
                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
                .build()
        );
        // Compute the RSA signature
        try {
            signedJWT.sign(new RSASSASigner(MockAuthorizationServer.RSA_KEY.toRSAPrivateKey()));
        } catch (JOSEException e) {
            throw new IllegalStateException("Could not start test", e);
        }
        return signedJWT.serialize();
    }

}
