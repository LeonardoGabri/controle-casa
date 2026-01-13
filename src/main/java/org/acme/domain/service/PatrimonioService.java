package org.acme.domain.service;

import org.acme.api.filter.PatrimonioFilter;
import org.acme.api.request.PatrimonioRequest;
import org.acme.domain.model.Patrimonio;

import java.util.List;
import java.util.UUID;

public interface PatrimonioService {
    public Patrimonio inserir(PatrimonioRequest patrimonioRequest);
    public Patrimonio atualizar(PatrimonioRequest patrimonioRequest, UUID id);
    public List<Patrimonio> listarComFiltros(PatrimonioFilter patrimonioFilter, int page, int size);
    public Patrimonio buscarPorId(UUID id);
    public void deletar(UUID id);

}
