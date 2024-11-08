package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.model.Despesa;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class DespesaRepository implements PanacheRepository<Despesa> {
    public Optional<Despesa> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }
}
