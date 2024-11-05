package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.model.Responsavel;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ResponsavelRepository implements PanacheRepository<Responsavel> {
    public Optional<Responsavel> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }
}
