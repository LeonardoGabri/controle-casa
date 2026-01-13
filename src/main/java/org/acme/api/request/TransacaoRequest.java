package org.acme.api.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransacaoRequest {
    private LocalDate dataTransacao;
    private String patrimonio;
    private String tipo;
    private BigDecimal valor;
    private String descricao;
    private String patrimonioOrigem;
    private String patrimonioDestino;
}
