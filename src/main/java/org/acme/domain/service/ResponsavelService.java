package org.acme.domain.service;

import org.acme.api.dto.ResponsavelDTO;
import org.acme.api.filter.ResponsavelFilter;
import org.acme.api.request.ResponsavelRequest;
import org.acme.domain.model.Responsavel;

import java.util.List;
import java.util.UUID;

public interface ResponsavelService {
    public ResponsavelDTO inserirResponsavel(ResponsavelRequest responsavelRequest);
    public ResponsavelDTO atualizarResponsavel(ResponsavelRequest responsavelRequest, UUID id);
    public List<ResponsavelDTO> listarResponsavelFiltros(ResponsavelFilter responsavelFilter, int page, int size);
    public ResponsavelDTO buscarResponsavelPorId(UUID id);

    public void deletarResponsavel(UUID id);

}
