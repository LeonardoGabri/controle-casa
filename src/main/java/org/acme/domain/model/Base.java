package org.acme.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Data;
import org.acme.auth.domain.model.Tenant;
import org.acme.infra.context.TenantContext;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
@FilterDef(
        name = "tenantFilter",
        parameters = @ParamDef(name = "tenant", type = String.class)
)
@Filter(
        name = "tenantFilter",
        condition = "tenant = :tenant"
)
public abstract class Base extends PanacheEntityBase {
    @Column(name = "tenant", nullable = false, updatable = false)
    private String tenant = String.valueOf(TenantContext.getTenant());

    @Column(name = "username", nullable = false, updatable = false)
    private String username = TenantContext.getUsername();

    @Column(name = "data_inclusao", nullable = false, updatable = false)
    private LocalDateTime dataInclusao = LocalDateTime.now();
}
