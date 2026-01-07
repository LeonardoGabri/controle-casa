package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.api.dto.ResumoMensalDTO;
import org.acme.domain.repository.BancoRepository;
import org.acme.domain.repository.ResumoMensalRepository;
import org.acme.domain.service.ResumoMensalService;
import org.acme.infra.validacao.ReferenciaCobrancaValidator;

import java.util.List;


@ApplicationScoped
public class ResumoMensalServiceImpl implements ResumoMensalService {

    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com nome = %s";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";

    private ResumoMensalRepository resumoMensalRepository;

    @Inject
    public ResumoMensalServiceImpl(ResumoMensalRepository repository) {
        this.resumoMensalRepository = repository;
    }

    @Override
    public List<ResumoMensalDTO> buscarResumoMensal(String referenciaCobranca) {
        return resumoMensalRepository.buscarFechamentoMensal(referenciaCobranca);
    }
}


