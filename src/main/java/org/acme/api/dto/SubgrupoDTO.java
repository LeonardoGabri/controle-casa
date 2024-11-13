package org.acme.api.dto;

import lombok.Builder;
import lombok.Data;
import org.acme.domain.model.Grupo;
import org.acme.domain.model.Subgrupo;

import java.util.UUID;

@Data
@Builder
public class SubgrupoDTO {
    private UUID id;
    private String nome;
    private Grupo grupo;

    public static SubgrupoDTO entityFromDTO(Subgrupo subgrupo){
        return new SubgrupoDTO(
                subgrupo.getId(),
                subgrupo.getNome(),
                subgrupo.getGrupo()
        );
    }
}
