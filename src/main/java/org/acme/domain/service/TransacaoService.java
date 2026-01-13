package org.acme.domain.service;

import org.acme.api.filter.PatrimonioFilter;
import org.acme.api.filter.TransacaoFilter;
import org.acme.api.request.PatrimonioRequest;
import org.acme.api.request.TransacaoRequest;
import org.acme.domain.model.Patrimonio;
import org.acme.domain.model.Transacao;

import java.util.List;
import java.util.UUID;

public interface TransacaoService {
    public Transacao inserir(TransacaoRequest transacaoRequest);
    public Transacao atualizar(TransacaoRequest transacaoRequest, UUID id);
    public List<Transacao> listarComFiltros(TransacaoFilter patrimonioFilter, int page, int size);
    public Transacao buscarPorId(UUID id);
    public void deletar(UUID id);

}
