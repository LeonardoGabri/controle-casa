package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.acme.api.dto.BancoDTO;
import org.acme.api.dto.ContaDTO;
import org.acme.api.dto.GrupoDTO;
import org.acme.api.filter.ContaFilter;
import org.acme.api.filter.GrupoFilter;
import org.acme.api.request.ContaRequest;
import org.acme.api.request.GrupoRequest;
import org.acme.domain.model.Banco;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Grupo;
import org.acme.domain.model.Responsavel;
import org.acme.domain.repository.ContaRepository;
import org.acme.domain.repository.GrupoRepository;
import org.acme.domain.service.BancoService;
import org.acme.domain.service.ContaService;
import org.acme.domain.service.GrupoService;
import org.acme.domain.service.ResponsavelService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ApplicationScoped
public class ContaServiceImpl implements ContaService {

    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com essas especificações";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private ContaRepository contaRepository;
    private BancoService bancoService;
    private ResponsavelService responsavelService;

    @Inject
    public ContaServiceImpl(ContaRepository contaRepository, BancoService bancoService, ResponsavelService responsavelService) {
        this.contaRepository = contaRepository;
        this.bancoService = bancoService;
        this.responsavelService = responsavelService;
    }

    @Transactional
    @Override
    public Conta inserirConta(ContaRequest contaRequest) {
        try{
            Banco banco = bancoService.buscarBancoPorId(UUID.fromString(contaRequest.getBancoId()));
            Responsavel responsavel = responsavelService.buscarResponsavelPorId(UUID.fromString(contaRequest.getResponsavelId()));

            Conta conta = Conta.builder()
                    .banco(banco)
                    .responsavel(responsavel)
                    .tipo(contaRequest.getTipo())
                    .build();

            contaRepository.persist(conta);
            return conta;
        }catch (Exception e){
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
    }

    @Override
    public Conta atualizarConta(ContaRequest contaRequest, UUID id) {
        Conta conta = this.buscarContaPorId(id);
        Banco banco = bancoService.buscarBancoPorId(UUID.fromString(contaRequest.getBancoId()));
        Responsavel responsavel = responsavelService.buscarResponsavelPorId(UUID.fromString(contaRequest.getResponsavelId()));

        try {
            conta.setBanco(banco);
            conta.setResponsavel(responsavel);
            conta.setTipo(contaRequest.getTipo());
            contaRepository.persist(conta);
            return conta;
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
    }

    @Override
    public List<Conta> listarContaFiltros(ContaFilter contaFilter, int page, int size) {
        return contaRepository.paginacaoComFiltros(contaFilter, page, size);
    }

    @Override
    public Conta buscarContaPorId(UUID id) {
        Conta conta = contaRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));
        return conta;
    }

    @Override
    public void deletarConta(UUID id) {
        try {
            Conta conta = buscarContaPorId(id);
            contaRepository.delete(conta);
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ERRO_AO_DELETAR));
        }
    }

    private Boolean buscarContaEResponsavel(UUID banco, UUID responsavel) {
        return contaRepository.findByContaResponsavel(banco, responsavel).isPresent();
    }
}
