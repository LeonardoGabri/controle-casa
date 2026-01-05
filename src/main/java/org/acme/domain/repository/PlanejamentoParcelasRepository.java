package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.api.filter.DespesaFilter;
import org.acme.domain.model.Despesa;
import org.acme.domain.model.PlanejamentoParcelas;

import java.util.*;

@ApplicationScoped
public class PlanejamentoParcelasRepository implements PanacheRepository<PlanejamentoParcelas> {
    public Optional<PlanejamentoParcelas> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }
}
