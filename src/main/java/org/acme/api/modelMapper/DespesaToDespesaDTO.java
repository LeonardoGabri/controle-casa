package org.acme.api.modelMapper;

import jakarta.inject.Inject;
import org.acme.api.dto.DespesaDTO;
import org.acme.api.dto.ParcelaDTO;
import org.acme.api.dto.PlanejamentoParcelasDTO;
import org.acme.domain.model.Despesa;
import org.acme.domain.model.Parcela;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.UUID;

public class DespesaToDespesaDTO extends PropertyMap<Despesa, DespesaDTO> {
    @Override
    protected void configure() {
        map().setId(source.getId());

        map().setConta(source.getConta());
        map().setFornecedorId(String.valueOf(source.getFornecedor().getId()));
        map().setSubgrupoId(String.valueOf(source.getSubgrupo().getId()));
        map().setDescricao(source.getDescricao());
        map().setDataLancamento(source.getDataLancamento());
        map().setNumeroParcelas(source.getNumeroParcelas());
        map().setValorTotal(source.getValorTotal());
        map().setValorTotalAtivo(source.getValorTotalAtivo());
        map().setReferenciaCobranca(source.getReferenciaCobranca());
    }
}
