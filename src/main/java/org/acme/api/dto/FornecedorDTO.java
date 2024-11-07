package org.acme.api.dto;

import lombok.Builder;
import lombok.Data;
import org.acme.domain.model.Banco;
import org.acme.domain.model.Fornecedor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
public class FornecedorDTO {
    private UUID id;
    private String nome;
    private String grupo;
    private BigDecimal valorTotalAtivo;
    private BigDecimal valorTotal;

    public static FornecedorDTO entityFromDTO(Fornecedor fornecedor){
        return new FornecedorDTO(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getGrupo() != null ? fornecedor.getGrupo().getNome() : null,
                Optional.ofNullable(fornecedor.getValorTotal()).orElse(BigDecimal.ZERO),
                Optional.ofNullable(fornecedor.getValorTotalAtivo()).orElse(BigDecimal.ZERO)
        );
    }
}
