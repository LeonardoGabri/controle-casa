package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.api.dto.ResumoDespesaPorContaDTO;
import org.acme.api.filter.DespesaFilter;
import org.acme.domain.model.Despesa;

import java.util.*;

@ApplicationScoped
public class DespesaRepository implements PanacheRepository<Despesa> {
    public Optional<Despesa> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public List<Despesa> paginacaoComFiltros(DespesaFilter despesaFilter, int page, int size) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (despesaFilter.getFornecedorId() != null && !despesaFilter.getFornecedorId().isEmpty()) {
            queryBuilder.append("fornecedor.id = :fornecedor_id");
            params.put("fornecedor_id", UUID.fromString(despesaFilter.getFornecedorId()));
        }

        if (despesaFilter.getContaId() != null) {
            if (queryBuilder.length() > 0) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("conta.id = :conta_id");
            params.put("conta_id", UUID.fromString(despesaFilter.getContaId()));
        }

        if (despesaFilter.getSubgrupoId() != null) {
            if (queryBuilder.length() > 0) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("subgrupo.id = :subgrupo_id");
            params.put("subgrupo_id", UUID.fromString(despesaFilter.getSubgrupoId()));
        }

        PanacheQuery<Despesa> query;

        if (queryBuilder.length() > 0) {
            query = find(queryBuilder.toString(), params);
        } else {
            query = findAll();
        }

        return query.list();
    }

    public List<ResumoDespesaPorContaDTO> buscarResumoPorConta() {

        return getEntityManager()
                .createQuery("""
            SELECT new org.acme.api.dto.ResumoDespesaPorContaDTO(
                CONCAT(c.banco.nome, ' - ', c.responsavel.nome),
                SUM(d.valorTotal)
            )
            FROM Despesa d
            JOIN d.conta c
            WHERE d.valorTotal > 0
            GROUP BY c.id, c.banco.nome, c.responsavel.nome
        """, ResumoDespesaPorContaDTO.class)
                .getResultList();
    }
}
