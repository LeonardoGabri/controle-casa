package org.acme.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.model.Grupo;
import org.acme.domain.model.Subgrupo;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubgrupoDTO {
    private UUID id;
    private String nome;
    private Grupo grupo;
}
