package org.acme.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.enums.SituacaoEnum;
import org.acme.domain.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DespesaDTO {

    private UUID id;
    private Conta conta;
    private String fornecedor;
    private Subgrupo subgrupo;
    private String descricao;
    private LocalDate dataLancamento;
    private Integer numeroParcelas;
    private BigDecimal valorTotal;
    private BigDecimal valorTotalAtivo;
    private String referenciaCobranca;
    private List<PlanejamentoParcelas> planejamentoParcelas;
    private List<Parcela> parcelas;
}
