package org.acme.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.model.Banco;
import org.acme.domain.model.Conta;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumoDespesaPorContaDTO {
    private String contaNome;
    private BigDecimal valorTotal;
}
