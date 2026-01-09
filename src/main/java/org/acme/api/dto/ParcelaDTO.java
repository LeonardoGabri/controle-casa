package org.acme.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Responsavel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParcelaDTO {
    private UUID id;
    private String responsavelId;
    private String responsavelNome;
    private LocalDate dataVencimento;
    private BigDecimal valor;
    private String parcelaAtual;
    private Integer totalParcelas;
    private String fornecedorId;
    private String fornecedorNome;
    private String despesaId;
    private Conta conta;
}
