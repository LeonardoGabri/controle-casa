package org.acme.infra.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.acme.domain.service.GrupoService;
import org.modelmapper.ModelMapper;

public class ModelMapperConfig {

    @Produces
    @ApplicationScoped
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
}
