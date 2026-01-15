package org.acme.infra.interceptor;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;
import org.acme.infra.context.TenantContext;
import org.acme.infra.tenant.TenantAware;
import org.hibernate.Session;

import java.util.UUID;

@TenantAware
@Interceptor
@Dependent
@Priority(Interceptor.Priority.APPLICATION)
public class TenantHibernateInterceptor {

    @Inject
    EntityManager entityManager;

    @AroundInvoke
    public Object applyTenantFilter(InvocationContext context) throws Exception {

        UUID tenantId = TenantContext.getTenant();

        if (tenantId != null) {
            Session session = entityManager.unwrap(Session.class);

            session.enableFilter("tenantFilter")
                    .setParameter("tenant", tenantId.toString());
        }

        return context.proceed();
    }
}