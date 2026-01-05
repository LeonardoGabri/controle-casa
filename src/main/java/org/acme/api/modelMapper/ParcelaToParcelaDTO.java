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
        map().setResponsavel(source.getResponsavel());
        map().setDataVencimento(source.getDataVencimento());
        map().setFornecedor(source.getDespesa().getFornecedor().getNome());
        map().setParcelaAtual(source.getParcelaAtual());
        map().setPorcentagemDivisao(source.getPorcentagemDivisao());
    }
}
