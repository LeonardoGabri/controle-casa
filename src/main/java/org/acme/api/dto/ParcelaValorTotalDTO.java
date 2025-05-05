package org.acme.api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
@Builder
public class ParcelaValorTotalDTO {
    List<ParcelaDTO> parcelas;
    BigDecimal valorTotal;

    public static ParcelaValorTotalDTO responseDto (List<ParcelaDTO> parcelasDTO){
        BigDecimal valorTotal = parcelasDTO.stream()
                .map(ParcelaDTO::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return ParcelaValorTotalDTO.builder()
                .parcelas(parcelasDTO)
                .valorTotal(valorTotal)
                .build();
    }
}
