package org.acme.domain.service;

import org.acme.api.dto.ContaDTO;
import org.acme.api.dto.ResponsavelDTO;
import org.acme.api.filter.ContaFilter;
import org.acme.api.filter.ResponsavelFilter;
import org.acme.api.request.ContaRequest;
import org.acme.api.request.ResponsavelRequest;
import org.acme.domain.model.Conta;

import java.util.List;
import java.util.UUID;

public interface ContaService {
    public Conta inserirConta(ContaRequest contaRequest);
    public Conta atualizarConta(ContaRequest contaRequest, UUID id);
    public List<Conta> listarContaFiltros(ContaFilter contaFilter, int page, int size);
    public Conta buscarContaPorId(UUID id);
    public void deletarConta(UUID id);

}
