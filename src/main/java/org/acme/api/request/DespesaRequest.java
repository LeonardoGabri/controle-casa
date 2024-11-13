package org.acme.api.request;

import lombok.Data;
import org.acme.domain.enums.SituacaoEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DespesaRequest {
    private String contaId;
    private String fornecedorId;
    private String subgrupoId;
    private LocalDateTime dataLancamento;
    private Integer mesInicioCobranca;
    private Integer anoInicioCobranca;
    private Integer numeroParcelas;
    private BigDecimal valorTotal;
    List<PlanejamentoParcelasRequest> planejamentoParcelas;
    private SituacaoEnum situacao;
}
