package org.acme.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.model.Banco;
import org.acme.domain.model.Parcela;
import org.acme.domain.model.Responsavel;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumoMensalDTO {
    private UUID devedorId;
    private String devedorNome;
    private UUID credorId;
    private String credorNome;
    private BigDecimal valor;
}
