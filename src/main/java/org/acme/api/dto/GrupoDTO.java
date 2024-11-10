package org.acme.api.dto;

import lombok.Builder;
import lombok.Data;
import org.acme.domain.model.Grupo;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
public class GrupoDTO {
    private UUID id;
    private String nome;
//    private BigDecimal valorTotalAtivo;
//    private BigDecimal valorTotal;

    public static GrupoDTO entityFromDTO(Grupo grupo){
        return new GrupoDTO(
                grupo.getId(),
                grupo.getNome()
        );
    }
}
