package org.acme.api.dto;

import io.quarkus.arc.All;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.model.Banco;
import org.acme.domain.model.Fornecedor;
import org.acme.domain.model.Subgrupo;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorDTO {
    private UUID id;
    private String nome;
    private Subgrupo subgrupo;
}
