package org.acme.domain.service;

import org.acme.api.filter.GrupoFilter;
import org.acme.api.request.CriptomoedaRequest;
import org.acme.api.request.GrupoRequest;
import org.acme.domain.model.Criptomoeda;
import org.acme.domain.model.Grupo;

import java.util.List;
import java.util.UUID;

public interface CriptomoedaService {
    public Criptomoeda inserir(CriptomoedaRequest request);
    public Criptomoeda atualizar(CriptomoedaRequest request, UUID id);
    public List<Criptomoeda> listar();
    public Criptomoeda buscarPorId(UUID id);
    public void deletar(UUID id);
    public Criptomoeda buscarPorCodigo(String codigo);

}
