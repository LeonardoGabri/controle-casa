package org.acme.api.request;

import lombok.Data;
import org.acme.domain.enums.SituacaoEnum;
import org.acme.infra.validacao.ReferenciaCobranca;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DespesaRequest {
    private String contaId;
    private String fornecedorId;
    private String subgrupoId;
    private LocalDate dataLancamento;
    @ReferenciaCobranca
    private String referenciaCobranca;
    private Integer numeroParcelas;
    private BigDecimal valorTotal;
    List<PlanejamentoParcelasRequest> planejamentoParcelas;
    private SituacaoEnum situacao;
}
