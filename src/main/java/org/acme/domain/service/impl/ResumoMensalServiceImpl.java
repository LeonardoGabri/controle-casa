package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.acme.api.dto.AcertoResponsavelDTO;
import org.acme.api.dto.ObrigacaoFinanceiraDTO;
import org.acme.domain.repository.ResumoMensalRepository;
import org.acme.domain.service.ResumoMensalService;
import org.acme.infra.tenant.TenantAware;

import java.util.List;

@TenantAware
@ApplicationScoped
public class ResumoMensalServiceImpl implements ResumoMensalService {

    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com nome = %s";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";

    private ResumoMensalRepository resumoMensalRepository;

    @PersistenceContext
    EntityManager em;

    @Inject
    public ResumoMensalServiceImpl(ResumoMensalRepository repository) {
        this.resumoMensalRepository = repository;
    }

    @Override
    public List<AcertoResponsavelDTO> buscarAcertoResponsavel(String referenciaCobranca) {
        return resumoMensalRepository.buscarAcertoResponsaveis(referenciaCobranca);
    }

    @Override
    public List<ObrigacaoFinanceiraDTO> buscarObrigacaoFinanceira(String referenciaCobranca) {
        return resumoMensalRepository.buscarObrigacoesFinanceiras(referenciaCobranca);
    }
}


