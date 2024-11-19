package org.acme.api.dto;

import lombok.Builder;
import lombok.Data;
import org.acme.domain.enums.SituacaoEnum;
import org.acme.domain.model.Parcela;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
public class ParcelaDTO {
    private UUID id;
    private UUID responsavelId;
    private String responsavelNome;
    private LocalDate dataVencimento;
    private BigDecimal valor;
    private SituacaoEnum situacao;
    private String parcelaAtual;
    private String despesaFornecedor;
    private Double porcentagemDivisao;
    private UUID despesaId;


    public static ParcelaDTO entityFromDTO(Parcela parcela){
        return new ParcelaDTO(
                parcela.getId(),
                parcela.getResponsavel().getId(),
                parcela.getResponsavel().getNome(),
                parcela.getDataVencimento(),
                parcela.getValor(),
                parcela.getSituacao(),
                parcela.getParcelaAtual(),
                parcela.getDespesa().getFornecedor().getNome(),
                parcela.getPorcentagemDivisao(),
                parcela.getDespesa().getId()
        );
    }

    public static List<ParcelaDTO> fromEntityList(List<Parcela> parcelas) {
        return parcelas.stream()
                .map(ParcelaDTO::entityFromDTO)
                .collect(Collectors.toList());
    }
}
