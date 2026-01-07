package org.acme.domain.service;

import org.acme.api.dto.ResumoDespesaPorContaDTO;
import org.acme.api.filter.DespesaFilter;
import org.acme.api.request.DespesaRequest;
import org.acme.domain.model.Despesa;

import java.util.List;
import java.util.UUID;

public interface DespesaService {
    public Despesa inserirDespesa(DespesaRequest despesaRequest);
    public Despesa atualizarDespesa(DespesaRequest despesaRequest, UUID id);
    public List<Despesa> listar(DespesaFilter despesaFilter, int page, int size);
    public Despesa buscarDespesaPorId(UUID id);
    public void deletarDespesa(UUID id);
    public List<ResumoDespesaPorContaDTO> buscarResumoPorConta();
}
