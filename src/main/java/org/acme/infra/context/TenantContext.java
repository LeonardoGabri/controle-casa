package org.acme.infra.context;

import org.acme.auth.domain.model.Tenant;

import java.util.UUID;

public class TenantContext {

    private static final ThreadLocal<UUID> TENANT = new ThreadLocal<>();
    private static final ThreadLocal<String> USERNAME = new ThreadLocal<>();

    public static void setTenant(UUID tenantId) {
        TENANT.set(tenantId);
    }

    public static UUID getTenant() {
        return TENANT.get();
    }

    public static void setUsername(String username) {
        USERNAME.set(username);
    }

    public static String getUsername() {
        return USERNAME.get();
    }

    public static void clear() {
        TENANT.remove();
        USERNAME.remove();
    }
}
