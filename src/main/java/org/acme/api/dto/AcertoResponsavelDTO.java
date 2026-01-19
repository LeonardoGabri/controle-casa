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
public class AcertoResponsavelDTO {
    private UUID devedorId;
    private String devedorNome;
    private UUID credorId;
    private String credorNome;
    private BigDecimal valor;
}
