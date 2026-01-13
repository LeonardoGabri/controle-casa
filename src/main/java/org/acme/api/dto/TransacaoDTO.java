package org.acme.api.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.enums.MoedaEnum;
import org.acme.domain.enums.TipoPatrimonioEnum;
import org.acme.domain.enums.TipoTransacaoEnum;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Patrimonio;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoDTO {
    private UUID id;
    private LocalDate dataTransacao;
    private PatrimonioDTO patrimonio;
    private TipoTransacaoEnum tipo;
    private BigDecimal valor;
    private String descricao;
    private PatrimonioDTO patrimonioOrigem;
    private PatrimonioDTO patrimonioDestino;
}
