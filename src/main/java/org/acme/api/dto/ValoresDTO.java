package org.acme.api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ValoresDTO {
    public BigDecimal valorTotal;
    public BigDecimal valorTotalAtivo;
}
