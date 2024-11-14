package org.acme.api.dto;

import lombok.Builder;
import lombok.Data;
import org.acme.domain.model.Banco;
import org.acme.domain.model.Fornecedor;
import org.acme.domain.model.Subgrupo;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
public class FornecedorDTO {
    private UUID id;
    private String nome;
    private Subgrupo subgrupo;

    public static FornecedorDTO entityFromDTO(Fornecedor fornecedor){
        return new FornecedorDTO(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getSubgrupo() != null ? fornecedor.getSubgrupo() : null
        );
    }
}
