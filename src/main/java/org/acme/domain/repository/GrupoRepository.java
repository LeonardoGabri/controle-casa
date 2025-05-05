package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.api.filter.GrupoFilter;
import org.acme.domain.model.Grupo;

import java.util.*;

@ApplicationScoped
public class GrupoRepository implements PanacheRepository<Grupo> {
    public Optional<Grupo> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public List<Grupo> paginacaoComFiltros(GrupoFilter grupoFilter, int page, int size) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (grupoFilter.getNome() != null && !grupoFilter.getNome().isEmpty()) {
            queryBuilder.append("upper(nome) like :nome");
            params.put("nome", "%" + grupoFilter.getNome().toUpperCase() + "%");
        }

        PanacheQuery<Grupo> query;

        if (queryBuilder.length() > 0) {
            query = find(queryBuilder.toString(), params);
        } else {
            query = findAll();
        }

        return query.list();
    }

}
