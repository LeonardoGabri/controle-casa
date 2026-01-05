package org.acme.api.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanejamentoParcelasRequest {
    private String responsavelId;
    private String despesaId;
    private Double porcentagemDivisao;
}

