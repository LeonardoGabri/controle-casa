package org.acme.api.modelMapper;

import org.acme.api.dto.DespesaDTO;
import org.acme.api.dto.ParcelaDTO;
import org.acme.domain.model.Despesa;
import org.acme.domain.model.Parcela;
import org.modelmapper.PropertyMap;

public class DespesaToDespesaDTO extends PropertyMap<Despesa, DespesaDTO> {
    @Override
    protected void configure() {
        map().setId(source.getId());

        map().setConta(source.getConta());
        map().setFornecedor(source.getFornecedor().getNome());
        map().setSubgrupo(source.getSubgrupo());
        map().setDescricao(source.getDescricao());
        map().setDataLancamento(source.getDataLancamento());
        map().setNumeroParcelas(source.getNumeroParcelas());
        map().setValorTotal(source.getValorTotal());
        map().setValorTotalAtivo(source.getValorTotalAtivo());
        map().setReferenciaCobranca(source.getReferenciaCobranca());
        map().setPlanejamentoParcelas(source.getPlanejamentoParcelas());
    }
}
