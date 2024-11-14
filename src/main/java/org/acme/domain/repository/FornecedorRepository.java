package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.api.filter.FornecedorFilter;
import org.acme.api.filter.GrupoFilter;
import org.acme.domain.model.Fornecedor;
import org.acme.domain.model.Grupo;

import java.util.*;

@ApplicationScoped
public class FornecedorRepository implements PanacheRepository<Fornecedor> {
    public Optional<Fornecedor> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public List<Fornecedor> paginacaoComFiltros(FornecedorFilter fornecedorFilter, int page, int size) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (fornecedorFilter.getNome() != null && !fornecedorFilter.getNome().isEmpty()) {
            queryBuilder.append("upper(nome) like :nome");
            params.put("nome", "%" + fornecedorFilter.getNome().toUpperCase() + "%");
        }

        if (fornecedorFilter.getSubgrupoId() != null && !fornecedorFilter.getSubgrupoId().isEmpty()) {
            if (queryBuilder.length() > 0) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("subgrupo.id = :subgrupo_id");
            params.put("subgrupo_id", UUID.fromString(fornecedorFilter.getSubgrupoId()));
        }

        PanacheQuery<Fornecedor> query;

        if (queryBuilder.length() > 0) {
            query = find(queryBuilder.toString(), params);
        } else {
            query = findAll();
        }

        query.page(Page.of(page, size));
        return query.list();
    }

}
