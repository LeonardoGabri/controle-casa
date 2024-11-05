package org.acme.domain.service;

import org.acme.api.dto.ResponsavelDTO;
import org.acme.api.request.ResponsavelRequest;

import java.util.UUID;

public interface ResponsavelService {
    public ResponsavelDTO inserirResponsavel(ResponsavelRequest responsavelRequest);
    public ResponsavelDTO atualizarResponsavel(ResponsavelRequest responsavelRequest, UUID id);
    public ResponsavelDTO listarResponsavelFiltros(String nome);
    public ResponsavelDTO buscarResponsavelPorId(UUID id);

    public void deletarResponsavel(UUID id);

}
