package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.api.filter.ResponsavelFilter;
import org.acme.domain.model.Responsavel;

import java.util.*;

@ApplicationScoped
public class ResponsavelRepository implements PanacheRepository<Responsavel> {
    public Optional<Responsavel> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public List<Responsavel> paginacaoComFiltros(ResponsavelFilter responsavelFilter, int page, int size) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (responsavelFilter.getNome() != null && !responsavelFilter.getNome().isEmpty()) {
            queryBuilder.append("nome like :nome");
            params.put("nome", "%" + responsavelFilter.getNome() + "%");
        }

        PanacheQuery<Responsavel> query;

        if (queryBuilder.length() > 0) {
            query = find(queryBuilder.toString(), params);
        } else {
            query = findAll();
        }

        query.page(Page.of(page, size));
        return query.list();
    }

}
