package org.acme.domain.service;

import org.acme.api.dto.GrupoDTO;
import org.acme.api.filter.GrupoFilter;
import org.acme.api.request.GrupoRequest;

import java.util.List;
import java.util.UUID;

public interface GrupoService {
    public GrupoDTO inserirGrupo(GrupoRequest grupoRequest);
    public GrupoDTO atualizarGrupo(GrupoRequest grupoRequest, UUID id);
    public List<GrupoDTO> listarGrupoFiltros(GrupoFilter grupoFilter, int page, int size);
    public GrupoDTO buscarGrupoPorId(UUID id);
    public void deletarGrupo(UUID id);

}
