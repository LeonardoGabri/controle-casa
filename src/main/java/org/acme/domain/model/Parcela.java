package org.acme.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.enums.SituacaoEnum;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "parcela")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@DynamicUpdate
public class Parcela extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Responsavel responsavel;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "porcentagem_divisao")
    private Double porcentagemDivisao;

    @Column(name = "parcela_atual")
    private String parcelaAtual;

    @ManyToOne
    @JoinColumn(name = "despesa_id")
    private Despesa despesa;
}
