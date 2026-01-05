package org.acme.domain.service;

import org.acme.api.dto.ContaDTO;
import org.acme.api.dto.FornecedorDTO;
import org.acme.api.filter.ContaFilter;
import org.acme.api.filter.FornecedorFilter;
import org.acme.api.request.ContaRequest;
import org.acme.api.request.FornecedorRequest;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Fornecedor;

import java.util.List;
import java.util.UUID;

public interface FornecedorService {
    public Fornecedor inserirFornecedor(FornecedorRequest fornecedorRequest);
    public Fornecedor atualizarFornecedor(FornecedorRequest fornecedorRequest, UUID id);
    public List<Fornecedor> listarFornecedorFiltros(FornecedorFilter fornecedorFilter, int page, int size);
    public Fornecedor buscarFornecedorPorId(UUID id);
    public void deletarFornecedor(UUID id);

}
