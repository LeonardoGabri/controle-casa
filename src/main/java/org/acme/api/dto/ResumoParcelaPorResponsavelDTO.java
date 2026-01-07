package org.acme.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumoParcelaPorResponsavelDTO {
    private String responsavelNome;
    private BigDecimal valorTotal;
}
