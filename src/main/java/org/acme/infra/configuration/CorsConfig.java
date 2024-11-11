package org.acme.infra.configuration;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
@ApplicationScoped
public class CorsConfig {
    @Inject
    Router router;

    public void setup(@Observes StartupEvent event) {
        CorsHandler corsHandler = CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.PUT)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("Authorization")
                .allowedHeader("Content-Type")
                .allowedHeader("Accept")
                .allowedHeader("X-Requested-With");

        router.route().handler(corsHandler);
    }
}
