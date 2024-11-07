package org.acme.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "fornecedor")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@DynamicUpdate
public class Fornecedor extends Base {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @ManyToOne(optional = true)
    @JoinColumn(name = "grupo_id", nullable = true )
    private Grupo grupo;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "valor_total_ativo")
    private BigDecimal valorTotalAtivo;
}
