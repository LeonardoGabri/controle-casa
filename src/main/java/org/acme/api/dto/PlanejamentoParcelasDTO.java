package org.acme.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.enums.TipoContaEnum;
import org.acme.domain.model.Despesa;
import org.acme.domain.model.Responsavel;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanejamentoParcelasDTO {
    private UUID id;
    private Double porcentagemDivisao;
    private String responsavel;
}
