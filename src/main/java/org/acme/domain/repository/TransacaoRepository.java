package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.api.filter.TransacaoFilter;
import org.acme.domain.model.Transacao;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@ApplicationScoped
public class TransacaoRepository implements PanacheRepository<Transacao> {
    public Optional<Transacao> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public List<Transacao> paginacaoComFiltros(TransacaoFilter transacaoFilter, int page, int size) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (transacaoFilter.getPatrimonio() != null && !transacaoFilter.getPatrimonio().isEmpty()) {
            queryBuilder.append("patrimonio.id = :patrimonio_id");
            params.put("patrimonio_id", UUID.fromString(transacaoFilter.getPatrimonio()));
        }

        if (transacaoFilter.getTipo() != null) {
            if (queryBuilder.length() > 0) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("tipo = :tipo");
            params.put("tipo", transacaoFilter.getTipo());
        }

        if (transacaoFilter.getDataIni() != null || transacaoFilter.getDataFim() != null) {

            if (queryBuilder.length() > 0) {
                queryBuilder.append(" and ");
            }

            if (transacaoFilter.getDataIni() != null && transacaoFilter.getDataFim() != null) {

                LocalDate dataIni = inicioMes(transacaoFilter.getDataIni());
                LocalDate dataFim = fimMes(transacaoFilter.getDataFim());

                queryBuilder.append("dataTransacao between :dataIni and :dataFim");
                params.put("dataIni", dataIni);
                params.put("dataFim", dataFim);

            } else if (transacaoFilter.getDataIni() != null) {

                LocalDate dataIni = inicioMes(transacaoFilter.getDataIni());

                queryBuilder.append("dataTransacao >= :dataIni");
                params.put("dataIni", dataIni);

            } else {

                LocalDate dataFim = fimMes(transacaoFilter.getDataFim());

                queryBuilder.append("dataTransacao <= :dataFim");
                params.put("dataFim", dataFim);
            }
        }

        PanacheQuery<Transacao> query;

        if (queryBuilder.length() > 0) {
            queryBuilder.append(" order by dataTransacao desc");
            query = find(queryBuilder.toString(), params);
        } else {
            query = find("order by dataTransacao desc");
        }

        return query.list();
    }

    private LocalDate inicioMes(String mesAno) {
        YearMonth ym = YearMonth.parse(
                mesAno,
                DateTimeFormatter.ofPattern("MM/yyyy")
        );
        return ym.atDay(1);
    }

    private LocalDate fimMes(String mesAno) {
        YearMonth ym = YearMonth.parse(
                mesAno,
                DateTimeFormatter.ofPattern("MM/yyyy")
        );
        return ym.atEndOfMonth();
    }

}
