package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.api.filter.ContaFilter;
import org.acme.api.filter.GrupoFilter;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Grupo;

import java.util.*;

@ApplicationScoped
public class ContaRepository implements PanacheRepository<Conta> {
    public Optional<Conta> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public Optional<Conta> findByContaResponsavel(UUID banco, UUID responsavel) {
        var params = Parameters
                .with("bancoId", banco)
                .and("responsavelId", responsavel)
                .map();
        return find("banco.id =:bancoId and responsavel.id =: responsavelId", params).firstResultOptional();
    }

    public List<Conta> paginacaoComFiltros(ContaFilter contaFilter, int page, int size) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (contaFilter.getBancoId() != null && !contaFilter.getBancoId().isEmpty()) {
            queryBuilder.append("banco.id = :banco_id");
            params.put("banco_id", UUID.fromString(contaFilter.getBancoId()));
        }

        if (contaFilter.getResponsavelId() != null && !contaFilter.getResponsavelId().isEmpty()) {
            if (queryBuilder.length() > 0) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("responsavel.id = :responsavel_id");
            params.put("responsavel_id", UUID.fromString(contaFilter.getResponsavelId()));
        }

        PanacheQuery<Conta> query;

        if (queryBuilder.length() > 0) {
            query = find(queryBuilder.toString(), params);
        } else {
            query = findAll();
        }

        return query.list();
    }

}
