package org.acme.api.dto;

import lombok.Builder;
import lombok.Data;
import org.acme.domain.model.Responsavel;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
public class ResponsavelDTO {
    private UUID id;
    private String nome;
    private BigDecimal valorTotalAtivo;
    private BigDecimal valorTotal;

    public static ResponsavelDTO entityFromDTO(Responsavel responsavel, ValoresDTO valoresDTO){
        return new ResponsavelDTO(
                responsavel.getId(),
                responsavel.getNome(),
                valoresDTO.getValorTotal(),
                valoresDTO.getValorTotalAtivo()
        );
    }
}
