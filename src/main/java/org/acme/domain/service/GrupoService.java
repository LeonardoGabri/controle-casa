package org.acme.domain.service;

import org.acme.api.dto.GrupoDTO;
import org.acme.api.filter.GrupoFilter;
import org.acme.api.request.GrupoRequest;
import org.acme.domain.model.Grupo;

import java.util.List;
import java.util.UUID;

public interface GrupoService {
    public Grupo inserirGrupo(GrupoRequest grupoRequest);
    public Grupo atualizarGrupo(GrupoRequest grupoRequest, UUID id);
    public List<Grupo> listarGrupoFiltros(GrupoFilter grupoFilter, int page, int size);
    public Grupo buscarGrupoPorId(UUID id);
    public void deletarGrupo(UUID id);

}
