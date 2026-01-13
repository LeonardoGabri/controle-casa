package org.acme.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.enums.TipoTransacaoEnum;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "transacao")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@DynamicUpdate
public class Transacao extends Base {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "data_transacao")
    private LocalDate dataTransacao;

    @ManyToOne
    @JoinColumn(name = "patrimonio_id")
    private Patrimonio patrimonio;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoTransacaoEnum tipo;

    @Column(name = "valor", precision = 20, scale = 8, nullable = false)
    private BigDecimal valor;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "patrimonio_origem_id")
    private Patrimonio patrimonioOrigem;

    @ManyToOne
    @JoinColumn(name = "patrimonio_destino_id")
    private Patrimonio patrimonioDestino;
}
