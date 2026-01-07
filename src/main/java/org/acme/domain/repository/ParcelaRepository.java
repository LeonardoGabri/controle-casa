package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.api.dto.ResumoDespesaPorContaDTO;
import org.acme.api.dto.ResumoParcelaPorResponsavelDTO;
import org.acme.api.dto.ValoresDTO;
import org.acme.api.filter.ParcelaFilter;
import org.acme.domain.enums.SituacaoEnum;
import org.acme.domain.model.Parcela;

import java.math.BigDecimal;
import java.util.*;

@ApplicationScoped
public class ParcelaRepository implements PanacheRepository<Parcela> {
    public Optional<Parcela> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public List<Parcela> paginacaoComFiltros(ParcelaFilter parcelaFilter, int page, int size) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (parcelaFilter.getResponsavelId() != null && !parcelaFilter.getResponsavelId().isEmpty()) {
            queryBuilder.append("responsavel.id = :responsavel_id");
            params.put("responsavel_id", UUID.fromString(parcelaFilter.getResponsavelId()));
        }

        if (parcelaFilter.getReferenciaCobranca() != null && !parcelaFilter.getReferenciaCobranca().isEmpty()) {
            String[] mesAno = parcelaFilter.getReferenciaCobranca().split("/");
            int mes = Integer.parseInt(mesAno[0]);
            int ano = Integer.parseInt(mesAno[1]);

            if (queryBuilder.length() > 0) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("MONTH(dataVencimento) = :mesReferencia and YEAR(dataVencimento) = :anoReferencia");
            params.put("mesReferencia", mes);
            params.put("anoReferencia", ano);
        }

        if (parcelaFilter.getContaId() != null && !parcelaFilter.getContaId().isEmpty()) {
            if (queryBuilder.length() > 0) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("despesa.conta.id = :conta_id");
            params.put("conta_id", UUID.fromString(parcelaFilter.getContaId()));
        }

        PanacheQuery<Parcela> query;

        if (queryBuilder.length() > 0) {
            query = find(queryBuilder.toString(), params);
        } else {
            query = findAll();
        }

        return query.list();
    }


    public List<ResumoParcelaPorResponsavelDTO> buscarResumoPorResponsavel(ParcelaFilter parcelaFilter) {

        StringBuilder jpql = new StringBuilder("""
        SELECT new org.acme.api.dto.ResumoParcelaPorResponsavelDTO(
            r.nome,
            SUM(p.valor)
        )
        FROM Parcela p
        JOIN p.responsavel r
        JOIN p.despesa d
        WHERE p.valor > 0
    """);

        Map<String, Object> params = new HashMap<>();

        if (parcelaFilter.getReferenciaCobranca() != null && !parcelaFilter.getReferenciaCobranca().isBlank()) {
            String[] mesAno = parcelaFilter.getReferenciaCobranca().split("/");
            int mes = Integer.parseInt(mesAno[0]);
            int ano = Integer.parseInt(mesAno[1]);

            jpql.append(" AND MONTH(p.dataVencimento) = :mes ");
            jpql.append(" AND YEAR(p.dataVencimento) = :ano ");

            params.put("mes", mes);
            params.put("ano", ano);
        }

        jpql.append(" GROUP BY r.id, r.nome ");

        var query = getEntityManager()
                .createQuery(jpql.toString(), ResumoParcelaPorResponsavelDTO.class);

        params.forEach(query::setParameter);

        return query.getResultList();
    }
}
