package org.acme.auth.domain.service;

import org.acme.auth.api.request.TenantRequest;
import org.acme.auth.domain.model.Tenant;

import java.util.List;
import java.util.UUID;

public interface TenantService {
    public Tenant inserirTenant(TenantRequest request);
    public List<Tenant> listarTenant();
    public Tenant buscarTenantPorId(UUID id);

    public void deletarTenant(UUID id);
}
