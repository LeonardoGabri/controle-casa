package org.acme.infra.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.acme.api.modelMapper.*;
import org.acme.domain.service.GrupoService;
import org.modelmapper.ModelMapper;

@ApplicationScoped
public class ModelMapperConfig {

    @Produces
    @ApplicationScoped
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new ContaToContaDTO());
        modelMapper.addMappings(new FornecedorToFornecedorDTO());
        modelMapper.addMappings(new ParcelaToParcelaDTO());
        modelMapper.addMappings(new DespesaToDespesaDTO());
        return modelMapper;
    }
}
