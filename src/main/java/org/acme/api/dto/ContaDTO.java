package org.acme.api.dto;

import lombok.Builder;
import lombok.Data;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Grupo;
import org.acme.domain.model.Responsavel;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
public class ContaDTO {
    private UUID id;
    private String nome;
//    private BigDecimal valorTotalAtivo;
//    private BigDecimal valorTotal;
    private String responsavel;

    public static ContaDTO entityFromDTO(Conta conta){
        return new ContaDTO(
                conta.getId(),
                conta.getBanco().getNome(),
                conta.getResponsavel().getNome()
        );
    }
}
