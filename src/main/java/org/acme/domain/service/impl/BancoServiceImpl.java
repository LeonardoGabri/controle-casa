package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.acme.api.dto.BancoDTO;
import org.acme.domain.model.Banco;
import org.acme.domain.repository.BancoRepository;
import org.acme.domain.service.BancoService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class BancoServiceImpl implements BancoService {

    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com nome = %s";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private BancoRepository bancoRepository;

    @Inject
    public BancoServiceImpl(BancoRepository bancoRepository){
        this.bancoRepository = bancoRepository;
    }

    @Override
    public BancoDTO inserirBanco() {
        return null;
    }

    @Override
    public BancoDTO atualizarConta() {
        return null;
    }

    @Override
    public List<BancoDTO> listar() {
        List<Banco> bancos = bancoRepository.findAll().list();
        return bancos.stream().map(BancoDTO::entityFromDTO).collect(Collectors.toList());
    }

    @Override
    public Banco buscarBancoPorId(UUID id) {
        return bancoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(MSG_NAO_ENCONTRADO, id)));
    }
}
