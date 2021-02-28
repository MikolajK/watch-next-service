package dev.mikolajk.watchnext.omdb;

import io.quarkus.arc.config.ConfigProperties;
import java.net.URI;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigProperties(prefix = "omdb")
public class OMDbConfiguration {

    private URI baseUrl;
    private String apiKey;
    private int timeoutSeconds = 5;

}
