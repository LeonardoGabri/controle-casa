package org.acme.api.dto;

import lombok.Builder;
import lombok.Data;
import org.acme.domain.enums.SituacaoEnum;
import org.acme.domain.model.Despesa;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class DespesaDTO {

    private UUID id;
    private String bancoNome;
    private String fornecedorNome;

    private String grupoNome;

    private Integer nParcelas;

    private BigDecimal valorTotal;

    private BigDecimal valorTotalAtivo;

    private Integer mesInicioCobranca;

    private Integer anoInicioCobranca;

    private SituacaoEnum situacao;

    private List<ParcelaDTO> parcelas;

    public static DespesaDTO entityFromDTO(Despesa despesa){
        return new DespesaDTO(
                despesa.getId(),
                despesa.getBanco().getNome(),
                despesa.getFornecedor().getNome(),
                despesa.getGrupo().getNome(),
                despesa.getNParcelas(),
                despesa.getValorTotal(),
                despesa.getValorTotalAtivo(),
                despesa.getMesInicioCobranca(),
                despesa.getAnoInicioCobranca(),
                despesa.getSituacao(),
                despesa.getParcelas() != null ? ParcelaDTO.fromEntityList(despesa.getParcelas()) : null
        );
    }


}
