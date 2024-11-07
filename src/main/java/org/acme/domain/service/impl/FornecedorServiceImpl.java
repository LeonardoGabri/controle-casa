package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.api.dto.ContaDTO;
import org.acme.api.dto.FornecedorDTO;
import org.acme.api.filter.ContaFilter;
import org.acme.api.filter.FornecedorFilter;
import org.acme.api.request.ContaRequest;
import org.acme.api.request.FornecedorRequest;
import org.acme.domain.model.*;
import org.acme.domain.repository.ContaRepository;
import org.acme.domain.repository.FornecedorRepository;
import org.acme.domain.service.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class FornecedorServiceImpl implements FornecedorService {

    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com essas especificações";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private FornecedorRepository fornecedorRepository;
    private GrupoService grupoService;

    private ModelMapper modelMapper;

    @Inject
    public FornecedorServiceImpl(FornecedorRepository fornecedorRepository, GrupoService grupoService, ModelMapper modelMapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.grupoService = grupoService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public FornecedorDTO inserirFornecedor(FornecedorRequest fornecedorRequest) {
        validaNomeFornecedor(fornecedorRequest, null);

        Grupo grupo = null;
        if (fornecedorRequest.getGrupoId() != null) {
            grupo = grupoService.buscarGrupoPorId(UUID.fromString(fornecedorRequest.getGrupoId()));
        }

        Fornecedor fornecedor = Fornecedor.builder()
                .nome(fornecedorRequest.getNome())
                .grupo(grupo)
                .build();

        fornecedorRepository.persist(fornecedor);
        return FornecedorDTO.entityFromDTO(fornecedor);
    }

    private void validaNomeFornecedor(FornecedorRequest fornecedorRequest, UUID fornecedorId) {
        boolean fornecedorEncontrado = fornecedorRepository.find("upper(nome) = ?1 and (id != ?2 or ?2 is null)",
                        fornecedorRequest.getNome().toUpperCase(), fornecedorId)
                .list().isEmpty();
        if (!fornecedorEncontrado) {
            throw new RuntimeException(String.format(MSG_DUPLICADO));
        }
    }

    @Override
    public FornecedorDTO atualizarFornecedor(FornecedorRequest fornecedorRequest, UUID id) {
        Fornecedor fornecedor = this.buscarFornecedorPorId(id);
        validaNomeFornecedor(fornecedorRequest, id);

        Grupo grupo = null;
        if (fornecedorRequest.getGrupoId() != null) {
            grupo = grupoService.buscarGrupoPorId(UUID.fromString(fornecedorRequest.getGrupoId()));
        }

        try {
            fornecedor.setNome(fornecedorRequest.getNome());
            fornecedor.setGrupo(grupo);
            fornecedorRepository.persist(fornecedor);
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
        return FornecedorDTO.entityFromDTO(fornecedor);
    }

    @Override
    public List<FornecedorDTO> listarFornecedorFiltros(FornecedorFilter fornecedorFilter, int page, int size) {
        List<Fornecedor> fornecedores = fornecedorRepository.paginacaoComFiltros(fornecedorFilter, page, size);

        return fornecedores.stream()
                .map(FornecedorDTO::entityFromDTO)
                .toList();
    }

    @Override
    public Fornecedor buscarFornecedorPorId(UUID id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));
        return fornecedor;
    }

    @Override
    public void deletarFornecedor(UUID id) {
        try {
            Fornecedor fornecedor = buscarFornecedorPorId(id);
            fornecedorRepository.delete(fornecedor);
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ERRO_AO_DELETAR));
        }
    }
}
