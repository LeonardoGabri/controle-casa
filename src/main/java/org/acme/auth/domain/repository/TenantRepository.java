package org.acme.auth.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.auth.domain.model.Tenant;

import java.util.Optional;
import java.util.UUID;
@ApplicationScoped
public class TenantRepository implements PanacheRepository<Tenant> {

    public Optional<Tenant> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }

}
