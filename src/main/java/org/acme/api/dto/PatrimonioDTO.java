package org.acme.api.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.enums.MoedaEnum;
import org.acme.domain.enums.TipoContaEnum;
import org.acme.domain.enums.TipoPatrimonioEnum;
import org.acme.domain.model.Conta;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatrimonioDTO {
    private UUID id;
    private Conta conta;
    private TipoPatrimonioEnum tipo;
    private MoedaEnum moeda;
    private BigDecimal valor;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String descricao;
}
