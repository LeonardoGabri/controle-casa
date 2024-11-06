package org.acme.domain.service;

import org.acme.api.dto.BancoDTO;
import org.acme.api.dto.ContaDTO;
import org.acme.api.filter.ContaFilter;
import org.acme.api.request.ContaRequest;
import org.acme.domain.model.Banco;

import java.util.List;
import java.util.UUID;

public interface BancoService {
    public BancoDTO inserirBanco();
    public BancoDTO atualizarConta();
    public List<BancoDTO> listar();
    public Banco buscarBancoPorId(UUID id);

}
