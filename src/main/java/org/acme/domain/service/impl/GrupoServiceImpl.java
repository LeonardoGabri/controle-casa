package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.api.dto.GrupoDTO;
import org.acme.api.filter.GrupoFilter;
import org.acme.api.request.GrupoRequest;
import org.acme.domain.model.Grupo;
import org.acme.domain.repository.GrupoRepository;
import org.acme.domain.service.GrupoService;

import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class GrupoServiceImpl implements GrupoService {

    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com nome = %s";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private GrupoRepository grupoRepository;

    @Inject
    public GrupoServiceImpl(GrupoRepository grupoRepository){
        this.grupoRepository = grupoRepository;
    }

    @Transactional
    @Override
    public GrupoDTO inserirGrupo(GrupoRequest grupoRequest) {
        validaNomeGrupo(grupoRequest, null);
        Grupo grupo = Grupo.builder()
                .nome(grupoRequest.getNome())
                .build();

        grupoRepository.persist(grupo);
        return GrupoDTO.entityFromDTO(grupo);
    }

    private void validaNomeGrupo(GrupoRequest grupoRequest, UUID grupoId) {
        boolean fornecedorEncontrado = grupoRepository.find("upper(nome) = ?1 and (id != ?2 or ?2 is null)",
                        grupoRequest.getNome().toUpperCase(), grupoId)
                .list().isEmpty();
        if (!fornecedorEncontrado) {
            throw new RuntimeException(String.format(MSG_DUPLICADO));
        }
    }

    @Override
    public GrupoDTO atualizarGrupo(GrupoRequest grupoRequest, UUID id) {
        Grupo grupo = this.buscarGrupoPorId(id);
        validaNomeGrupo(grupoRequest, id);
        try{
            grupo.setNome(grupoRequest.getNome());
            grupoRepository.persist(grupo);
        }catch (RuntimeException e){
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
        return GrupoDTO.entityFromDTO(grupo);
    }

    @Override
    public List<GrupoDTO> listarGrupoFiltros(GrupoFilter grupoFilter, int page, int size) {
        List<Grupo> grupos = grupoRepository.paginacaoComFiltros(grupoFilter,page, size);

        return grupos.stream()
                .map(GrupoDTO::entityFromDTO)
                .toList();
    }

    @Override
    public Grupo buscarGrupoPorId(UUID id) {
        Grupo grupo = grupoRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));
        return grupo;
    }

    @Override
    public void deletarGrupo(UUID id) {
        try{
            Grupo grupo = buscarGrupoPorId(id);
            grupoRepository.delete(grupo);
        }catch (RuntimeException e){
            throw new RuntimeException(String.format(ERRO_AO_DELETAR));
        }
    }
}
