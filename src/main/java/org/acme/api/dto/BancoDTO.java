package org.acme.api.dto;

import lombok.Builder;
import lombok.Data;
import org.acme.domain.model.Banco;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Grupo;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
public class BancoDTO {
    private UUID id;
    private String nome;
    private String numero;

    public static BancoDTO entityFromDTO(Banco banco){
        return new BancoDTO(
                banco.getId(),
                banco.getNome(),
                banco.getNumero()
        );
    }
}
