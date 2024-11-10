package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.acme.api.dto.ResponsavelDTO;
import org.acme.api.dto.ValoresDTO;
import org.acme.api.filter.ResponsavelFilter;
import org.acme.api.request.ResponsavelRequest;
import org.acme.domain.model.Responsavel;
import org.acme.domain.repository.ResponsavelRepository;
import org.acme.domain.service.ParcelaService;
import org.acme.domain.service.ResponsavelService;

import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class ResponsavelServiceImpl implements ResponsavelService {

    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com nome = %s";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private ResponsavelRepository responsavelRepository;
    private ParcelaService parcelaService;

    @Inject
    public ResponsavelServiceImpl(ResponsavelRepository responsavelRepository, ParcelaService parcelaService){
        this.responsavelRepository = responsavelRepository;
        this.parcelaService = parcelaService;
    }

    @Transactional
    @Override
    public Responsavel inserirResponsavel(ResponsavelRequest responsavelRequest) {
        validaNomeResponsavel(responsavelRequest, null);
        Responsavel responsavel = Responsavel.builder()
                .nome(responsavelRequest.getNome())
                .build();

        responsavelRepository.persist(responsavel);
        return responsavel;
    }

    @Override
    public Responsavel atualizarResponsavel(ResponsavelRequest responsavelRequest, UUID id) {
        Responsavel responsavel = this.buscarPorId(id);
        validaNomeResponsavel(responsavelRequest, id);
        try{
            responsavel.setNome(responsavelRequest.getNome());
            responsavelRepository.persist(responsavel);
        }catch (RuntimeException e){
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
        return responsavel;
    }

    private void validaNomeResponsavel(ResponsavelRequest responsavelRequest, UUID responsavelId) {
        boolean fornecedorEncontrado = responsavelRepository.find("upper(nome) = ?1 and (id != ?2 or ?2 is null)",
                        responsavelRequest.getNome().toUpperCase(), responsavelId)
                .list().isEmpty();
        if (!fornecedorEncontrado) {
            throw new RuntimeException(String.format(MSG_DUPLICADO));
        }
    }

    @Override
    public List<Responsavel> listarResponsavelFiltros(ResponsavelFilter responsavelFilter, int page, int size) {
        List<Responsavel> responsaveis = responsavelRepository.paginacaoComFiltros(responsavelFilter,page, size);

        return responsaveis;
    }

    @Override
    public Responsavel buscarResponsavelPorId(UUID id) {
        Responsavel responsavel = responsavelRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));

        return responsavel;
    }

    @Override
    public void deletarResponsavel(UUID id) {
        try{
            Responsavel responsavel = buscarPorId(id);
            responsavelRepository.delete(responsavel);
        }catch (RuntimeException e){
            throw new RuntimeException(String.format(ERRO_AO_DELETAR));
        }
    }

    private Responsavel buscarPorId(UUID id){
        return responsavelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(MSG_NAO_ENCONTRADO, id)));
    }
}
