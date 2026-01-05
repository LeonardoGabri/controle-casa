package org.acme.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.enums.SituacaoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParcelaRequest {
    private String responsavelId;
    private LocalDate dataVencimento;
    private BigDecimal valor;
    private Double porcentagemDivisao;
    private String despesaId;
    private String parcelaAtual;
}
