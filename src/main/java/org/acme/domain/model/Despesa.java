package org.acme.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.enums.SituacaoEnum;
import org.acme.infra.tenant.TenantAware;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "despesa")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@DynamicUpdate
public class Despesa extends Base {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @Column(name = "data_lancamento")
    private LocalDate dataLancamento;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "subgrupo_id")
    private Subgrupo subgrupo;

    @Column(name = "n_parcelas")
    private Integer numeroParcelas;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "valor_total_ativo")
    private BigDecimal valorTotalAtivo;

    @Column(name = "referencia_cobranca")
    private String referenciaCobranca;

    @OneToMany(mappedBy = "despesa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonbTransient
    private List<PlanejamentoParcelas> planejamentoParcelas;

    @OneToMany(mappedBy = "despesa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonbTransient
    @JsonManagedReference
    private List<Parcela> parcelas;
}
