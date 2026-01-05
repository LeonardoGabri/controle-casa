package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.api.filter.FornecedorFilter;
import org.acme.api.request.FornecedorRequest;
import org.acme.domain.model.Fornecedor;
import org.acme.domain.model.Subgrupo;
import org.acme.domain.repository.FornecedorRepository;
import org.acme.domain.service.FornecedorService;
import org.acme.domain.service.SubgrupoService;
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
    private SubgrupoService subgrupoService;

    @Inject
    public FornecedorServiceImpl(FornecedorRepository fornecedorRepository, SubgrupoService subgrupoService) {
        this.fornecedorRepository = fornecedorRepository;
        this.subgrupoService = subgrupoService;
    }

    @Transactional
    @Override
    public Fornecedor inserirFornecedor(FornecedorRequest fornecedorRequest) {
        try{
            validaNomeFornecedor(fornecedorRequest, null);

            Subgrupo subgrupo = null;
            if (fornecedorRequest.getSubgrupoId() != null) {
                subgrupo = subgrupoService.buscarSubgrupoPorId(UUID.fromString(fornecedorRequest.getSubgrupoId()));
            }

            Fornecedor fornecedor = Fornecedor.builder()
                    .nome(fornecedorRequest.getNome())
                    .subgrupo(subgrupo)
                    .build();

            fornecedorRepository.persist(fornecedor);
            return fornecedor;
        }catch (Exception e){
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
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
    public Fornecedor atualizarFornecedor(FornecedorRequest fornecedorRequest, UUID id) {
        Fornecedor fornecedor = this.buscarFornecedorPorId(id);
        validaNomeFornecedor(fornecedorRequest, id);

        Subgrupo subgrupo = null;
        if (fornecedorRequest.getSubgrupoId() != null) {
            subgrupo = subgrupoService.buscarSubgrupoPorId(UUID.fromString(fornecedorRequest.getSubgrupoId()));
        }

        try {
            fornecedor.setNome(fornecedorRequest.getNome());
            fornecedor.setSubgrupo(subgrupo);
            fornecedorRepository.persist(fornecedor);
            return fornecedor;
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
    }

    @Override
    public List<Fornecedor> listarFornecedorFiltros(FornecedorFilter fornecedorFilter, int page, int size) {
        return fornecedorRepository.paginacaoComFiltros(fornecedorFilter, page, size);
    }

    @Override
    public Fornecedor buscarFornecedorPorId(UUID id) {
        return fornecedorRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));
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
