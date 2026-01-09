package org.acme.api.modelMapper;

import org.acme.api.dto.FornecedorDTO;
import org.acme.api.dto.ParcelaDTO;
import org.acme.domain.model.Fornecedor;
import org.acme.domain.model.Parcela;
import org.modelmapper.PropertyMap;

public class ParcelaToParcelaDTO extends PropertyMap<Parcela, ParcelaDTO> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setResponsavelId(String.valueOf(source.getResponsavel().getId()));
        map().setDataVencimento(source.getDataVencimento());
        map().setFornecedorId(String.valueOf(source.getDespesa().getFornecedor().getId()));
        map().setFornecedorNome(String.valueOf(source.getDespesa().getFornecedor().getNome()));
        map().setParcelaAtual(source.getParcelaAtual());
        map().setDespesaId(String.valueOf(source.getDespesa().getId()));
        map().setConta(source.getDespesa().getConta());
        map().setTotalParcelas(source.getDespesa().getNumeroParcelas());
    }
}
