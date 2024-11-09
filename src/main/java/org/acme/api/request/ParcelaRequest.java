package org.acme.api.request;

import lombok.Builder;
import lombok.Data;
import org.acme.domain.enums.SituacaoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ParcelaRequest {
    private String responsavelId;
    private LocalDate dataVencimento;
    private BigDecimal valor;
    private SituacaoEnum situacao;
    private Double porcetagemDivisao;
    private String despesaId;
}
