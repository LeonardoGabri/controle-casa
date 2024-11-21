package org.acme.api.dto;

import lombok.Builder;
import lombok.Data;
import org.acme.domain.enums.SituacaoEnum;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Despesa;
import org.acme.domain.model.Fornecedor;
import org.acme.domain.model.Subgrupo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class DespesaDTO {

    private UUID id;
    private Conta conta;
    private Fornecedor fornecedor;
    private Subgrupo subgrupo;
    private LocalDate dataLancamento;
    private Integer numeroParcelas;
    private BigDecimal valorTotal;
    private BigDecimal valorTotalAtivo;
    private String referenciaCobranca;
    private List<ParcelaDTO> parcelas;

    public static DespesaDTO entityFromDTO(Despesa despesa){
        return new DespesaDTO(
                despesa.getId(),
                despesa.getConta(),
                despesa.getFornecedor(),
                despesa.getSubgrupo(),
                despesa.getDataLancamento(),
                despesa.getNumeroParcelas(),
                despesa.getValorTotal(),
                despesa.getValorTotalAtivo(),
                despesa.getReferenciaCobranca(),
                despesa.getParcelas() != null ? ParcelaDTO.fromEntityList(despesa.getParcelas()) : null
        );
    }


}
