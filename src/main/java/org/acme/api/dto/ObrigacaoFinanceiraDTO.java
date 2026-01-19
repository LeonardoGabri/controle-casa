package org.acme.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObrigacaoFinanceiraDTO {
    private UUID responsavelId;
    private String responsavelNome;

    private UUID bancoId;
    private String bancoNome;

    private UUID contaId;

    private BigDecimal valorTotal;
}
