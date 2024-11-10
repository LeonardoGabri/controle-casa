package org.acme.domain.service;

import org.acme.api.dto.ResponsavelDTO;
import org.acme.api.filter.ResponsavelFilter;
import org.acme.api.request.ResponsavelRequest;
import org.acme.domain.model.Responsavel;

import java.util.List;
import java.util.UUID;

public interface ResponsavelService {
    public Responsavel inserirResponsavel(ResponsavelRequest responsavelRequest);
    public Responsavel atualizarResponsavel(ResponsavelRequest responsavelRequest, UUID id);
    public List<Responsavel> listarResponsavelFiltros(ResponsavelFilter responsavelFilter, int page, int size);
    public Responsavel buscarResponsavelPorId(UUID id);

    public void deletarResponsavel(UUID id);

}
