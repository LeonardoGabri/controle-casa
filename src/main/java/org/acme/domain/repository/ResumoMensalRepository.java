package org.acme.domain.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.acme.api.dto.ResumoMensalDTO;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApplicationScoped
public class ResumoMensalRepository {
    @PersistenceContext
    EntityManager em;

    public List<ResumoMensalDTO> buscarFechamentoMensal(String referenciaCobranca) {

        YearMonth ym = YearMonth.parse(
                referenciaCobranca,
                DateTimeFormatter.ofPattern("MM/yyyy")
        );

        LocalDate dataInicio = ym.atDay(1);
        LocalDate dataFim = ym.atEndOfMonth();

        return em.createQuery("""
                            SELECT new org.acme.api.dto.ResumoMensalDTO(
                                r.id,
                                r.nome,
                                cr.id,
                                cr.nome,
                                SUM(p.valor)
                            )
                            FROM Parcela p
                                JOIN p.responsavel r
                                JOIN p.despesa d
                                JOIN d.conta c
                                JOIN c.responsavel cr
                            WHERE p.dataVencimento BETWEEN :ini AND :fim
                              AND r.id <> cr.id
                            GROUP BY r.id, r.nome, cr.id, cr.nome
                            ORDER BY cr.nome, r.nome
                        """, ResumoMensalDTO.class)
                .setParameter("ini", dataInicio)
                .setParameter("fim", dataFim)
                .getResultList();
    }

}
