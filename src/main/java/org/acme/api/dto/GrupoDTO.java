package org.acme.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.model.Grupo;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GrupoDTO {
    private UUID id;
    private String nome;
//    private BigDecimal valorTotalAtivo;
//    private BigDecimal valorTotal;
}
