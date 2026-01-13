package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.api.filter.PatrimonioFilter;
import org.acme.domain.model.Criptomoeda;
import org.acme.domain.model.Patrimonio;

import java.util.*;

@ApplicationScoped
public class CriptomoedaRepository implements PanacheRepository<Criptomoeda> {
    public Optional<Criptomoeda> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }
}
