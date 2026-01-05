package org.acme.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "planejamento_parcelas")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@DynamicUpdate
public class PlanejamentoParcelas extends Base  {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "despesa_id")
    private Despesa despesa;

    @Column(name = "porcentagem_divisao")
    private Double porcentagemDivisao;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Responsavel responsavel;
}
