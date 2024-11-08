package org.acme.domain.service;

import org.acme.api.request.DespesaRequest;
import org.acme.domain.model.Despesa;

import java.util.List;
import java.util.UUID;

public interface DespesaService {
    public Despesa inserirDespesa(DespesaRequest despesaRequest);
    public Despesa atualizarDespesa();
    public List<Despesa> listar();
    public Despesa buscarDespesaPorId(UUID id);
}
