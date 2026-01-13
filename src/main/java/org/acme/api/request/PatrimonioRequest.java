package org.acme.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.enums.TipoContaEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatrimonioRequest {
    private String conta;
    private String tipo;
    private String moeda;
    private BigDecimal valor;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String descricao;
}
