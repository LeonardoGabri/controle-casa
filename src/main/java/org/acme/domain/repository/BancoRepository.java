package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.api.filter.ContaFilter;
import org.acme.domain.model.Banco;
import org.acme.domain.model.Conta;

import java.util.*;

@ApplicationScoped
public class BancoRepository implements PanacheRepository<Banco> {
    public Optional<Banco> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }
}
