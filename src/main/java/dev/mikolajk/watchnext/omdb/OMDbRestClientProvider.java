package dev.mikolajk.watchnext.omdb;

import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.jboss.resteasy.microprofile.client.RestClientBuilderImpl;

@Dependent
public class OMDbRestClientProvider {

    private final OMDbConfiguration omdbConfiguration;

    @Inject
    public OMDbRestClientProvider(OMDbConfiguration omdbConfiguration) {
        this.omdbConfiguration = omdbConfiguration;
    }

    @Produces
    @ApplicationScoped
    public OMDbRestClient omdbRestClient() {
        return new RestClientBuilderImpl()
            .baseUri(omdbConfiguration.getBaseUrl())
            .readTimeout(omdbConfiguration.getTimeoutSeconds(), TimeUnit.SECONDS)
            .build(OMDbRestClient.class);
    }

}
