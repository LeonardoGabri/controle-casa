package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
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
            // Extrai mês e ano do formato "MM/AAAA"
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

        if (parcelaFilter.getSituacao() != null) {
            if (queryBuilder.length() > 0) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("situacao = :situacao");
            params.put("situacao", parcelaFilter.getSituacao());
        }

        PanacheQuery<Parcela> query;

        if (queryBuilder.length() > 0) {
            query = find(queryBuilder.toString(), params);
        } else {
            query = findAll();
        }

        query.page(Page.of(page, size));
        return query.list();
    }

    public ValoresDTO findValoresByResponsavelId(UUID responsavelId) {
        Object[] result = (Object[]) getEntityManager()
                .createQuery(
                        "SELECT COALESCE(SUM(p.valor), 0), " +
                                "COALESCE(SUM(CASE WHEN p.situacao = :situacao THEN p.valor ELSE 0 END), 0) " +
                                "FROM Parcela p WHERE p.responsavel.id = :responsavelId")
                .setParameter("responsavelId", responsavelId)
                .setParameter("situacao", SituacaoEnum.ABERTA)
                .getSingleResult();

        return ValoresDTO.builder()
                .valorTotal((BigDecimal) result[0])
                .valorTotalAtivo((BigDecimal) result[1])
                .build();
    }


}
