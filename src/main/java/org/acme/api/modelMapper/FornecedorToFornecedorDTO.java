package org.acme.api.modelMapper;

import org.acme.api.dto.ContaDTO;
import org.acme.api.dto.FornecedorDTO;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Fornecedor;
import org.modelmapper.PropertyMap;

public class FornecedorToFornecedorDTO extends PropertyMap<Fornecedor, FornecedorDTO> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setNome(source.getNome());
        map().setSubgrupo(source.getSubgrupo() != null ? source.getSubgrupo() : null);

    }
}
