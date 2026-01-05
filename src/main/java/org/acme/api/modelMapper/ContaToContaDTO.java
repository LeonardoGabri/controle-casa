package org.acme.api.modelMapper;

import org.acme.api.dto.ContaDTO;
import org.acme.domain.model.Conta;
import org.modelmapper.PropertyMap;

public class ContaToContaDTO extends PropertyMap<Conta, ContaDTO> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setNome(source.getBanco().getNome());
        map().setResponsavel(source.getResponsavel().getNome());
        map().setTipo(source.getTipo());
    }
}
