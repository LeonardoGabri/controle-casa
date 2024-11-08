package org.acme.api.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanejamentoParcelasRequest {
    private String responsavelId;
    private Double porcentagemDivisao;
    private BigDecimal valor;
}

