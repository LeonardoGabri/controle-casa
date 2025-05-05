package org.acme.auth.domain.service;

import org.acme.auth.api.request.RolesRequest;
import org.acme.auth.api.request.TenantRequest;
import org.acme.auth.domain.model.Roles;
import org.acme.auth.domain.model.Tenant;

import java.util.List;
import java.util.UUID;

public interface RolesService {
    public Roles inserirRoles(RolesRequest request);
    public List<Roles> listarRoles();
    public Roles buscarRolesPorId(UUID id);

    public void deletarRoles(UUID id);
}
