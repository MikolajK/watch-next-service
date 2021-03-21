package dev.mikolajk.watchnext;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.testcontainers.shaded.javax.ws.rs.core.Response;

/**
 * From https://www.jasondl.ee/posts/2021/securing-and-testing-quarkus.html
 */
public class MockAuthorizationServer implements QuarkusTestResourceLifecycleManager {
    private WireMockServer wireMockServer;
    public static final RSAKey RSA_KEY;

    static {
        try {
            RSA_KEY = new RSAKeyGenerator(2048)
                .keyID("123")
                .keyUse(KeyUse.SIGNATURE)
                .generate();
        } catch (JOSEException e) {
            throw new IllegalStateException("Could not start test", e);
        }
    }

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        postStubMapping(oidcConfigurationStub());
        postStubMapping(publicKeysStub(RSA_KEY.toPublicJWK().toJSONString()));

        Map<String,String> props = new HashMap<>();
        props.put("quarkus.oidc.auth-server-url", wireMockServer.baseUrl() + "/mock-server");
        props.put("wiremock.url", wireMockServer.baseUrl());
        return props;
    }

    @Override
    public void stop() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    private ResponseBody<?> postStubMapping(String request) {
        RestAssured.baseURI = wireMockServer.baseUrl();
        return RestAssured.given()
            .body(request)
            .post("/__admin/mappings")
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode())
            .extract()
            .response()
            .body();
    }

    private String oidcConfigurationStub() {
        return readFile("/oidcconfig.json")
            .replace("$baseUrl", wireMockServer.baseUrl());
    }

    private String publicKeysStub(String keys) {
        return readFile("/publickey.json")
            .replace("$keys", keys);
    }

    private String readFile(String fileName) {
        return new Scanner(getClass()
            .getResourceAsStream(fileName), "UTF-8")
            .useDelimiter("\\A")
            .next();
    }
}