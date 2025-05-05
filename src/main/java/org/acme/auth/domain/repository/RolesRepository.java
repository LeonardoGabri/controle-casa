package org.acme.auth.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.auth.domain.model.Roles;

import java.util.Optional;
import java.util.UUID;
@ApplicationScoped
public class RolesRepository implements PanacheRepository<Roles> {
    public Optional<Roles> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }

}
