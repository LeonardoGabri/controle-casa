package org.acme.domain.service;

import org.acme.api.dto.SubgrupoDTO;
import org.acme.api.filter.SubgrupoFilter;
import org.acme.api.request.SubgrupoRequest;
import org.acme.domain.model.Subgrupo;

import java.util.List;
import java.util.UUID;

public interface SubgrupoService {
    public Subgrupo inserirSubgrupo(SubgrupoRequest subgrupoRequest);
    public Subgrupo atualizarSubgrupo(SubgrupoRequest subgrupoRequest, UUID id);
    public List<SubgrupoDTO> listarSubgrupoFiltros(SubgrupoFilter subgrupoFilter, int page, int size);
    public Subgrupo buscarSubgrupoPorId(UUID id);
    public void deletarSubgrupo(UUID id);

}
