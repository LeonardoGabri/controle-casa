package org.acme.infra.filter;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.acme.infra.context.TenantContext;
import org.hibernate.Session;

@Provider
public class RequestContextCleanupFilter implements ContainerResponseFilter {
    @Inject
    EntityManager entityManager;

    @Override
    public void filter(
            ContainerRequestContext requestContext,
            ContainerResponseContext responseContext
    ) {
        try {
            Session session = entityManager.unwrap(Session.class);
            session.disableFilter("tenantFilter");
        } catch (Exception ignored) {}

        TenantContext.clear();
    }
}
