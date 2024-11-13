package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.api.filter.GrupoFilter;
import org.acme.api.filter.SubgrupoFilter;
import org.acme.domain.model.Grupo;
import org.acme.domain.model.Subgrupo;

import java.util.*;

@ApplicationScoped
public class SubgrupoRepository implements PanacheRepository<Subgrupo> {
    public Optional<Subgrupo> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public List<Subgrupo> paginacaoComFiltros(SubgrupoFilter subgrupoFilter, int page, int size) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (subgrupoFilter.getNome() != null && !subgrupoFilter.getNome().isEmpty()) {
            queryBuilder.append("upper(nome) like :nome");
            params.put("nome", "%" + subgrupoFilter.getNome().toUpperCase() + "%");
        }

        if (subgrupoFilter.getGrupoId() != null) {
            if (queryBuilder.length() > 0) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("subgrupo.id = :subgrupo_id");
            params.put("subgrupo_id", subgrupoFilter.getGrupoId());
        }

        PanacheQuery<Subgrupo> query;

        if (queryBuilder.length() > 0) {
            query = find(queryBuilder.toString(), params);
        } else {
            query = findAll();
        }

        query.page(Page.of(page, size));
        return query.list();
    }

}
