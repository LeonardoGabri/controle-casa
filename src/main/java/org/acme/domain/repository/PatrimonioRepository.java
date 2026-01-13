package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.api.filter.FornecedorFilter;
import org.acme.api.filter.PatrimonioFilter;
import org.acme.domain.model.Fornecedor;
import org.acme.domain.model.Patrimonio;

import java.util.*;

@ApplicationScoped
public class PatrimonioRepository implements PanacheRepository<Patrimonio> {
    public Optional<Patrimonio> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public List<Patrimonio> paginacaoComFiltros(PatrimonioFilter patrimonioFilter, int page, int size) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (patrimonioFilter.getConta() != null && !patrimonioFilter.getConta().isEmpty()) {
            queryBuilder.append("conta.id = :conta_id");
            params.put("conta_id", "%" + patrimonioFilter.getConta() + "%");
        }

        if (patrimonioFilter.getTipo() != null) {
            if (queryBuilder.length() > 0) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("tipo = :tipo");
            params.put("tipo", patrimonioFilter.getTipo());
        }

        PanacheQuery<Patrimonio> query;

        if (queryBuilder.length() > 0) {
            query = find(queryBuilder.toString(), params);
        } else {
            query = findAll();
        }

        return query.list();
    }

}
