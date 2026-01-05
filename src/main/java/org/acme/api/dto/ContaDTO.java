package org.acme.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.enums.TipoContaEnum;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Grupo;
import org.acme.domain.model.Responsavel;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContaDTO {
    private UUID id;
    private String nome;
    private String responsavel;
    private TipoContaEnum tipo;
}
