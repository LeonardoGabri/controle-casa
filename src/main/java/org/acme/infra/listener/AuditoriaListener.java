package org.acme.infra.listener;

import jakarta.persistence.PrePersist;
import org.acme.auth.domain.model.Tenant;
import org.acme.domain.model.Base;
import org.acme.infra.context.TenantContext;

import java.time.LocalDateTime;

public class AuditoriaListener {

    @PrePersist
    public void beforePersist(Base entidade) {

        entidade.setTenant(String.valueOf(TenantContext.getTenant()));
        entidade.setUsername(TenantContext.getUsername());
        entidade.setDataInclusao(LocalDateTime.now());
    }
}
