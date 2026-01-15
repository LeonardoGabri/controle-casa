package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.api.dto.GrupoDTO;
import org.acme.api.dto.SubgrupoDTO;
import org.acme.api.filter.SubgrupoFilter;
import org.acme.api.request.GrupoRequest;
import org.acme.api.request.SubgrupoRequest;
import org.acme.domain.model.Grupo;
import org.acme.domain.model.Subgrupo;
import org.acme.domain.repository.SubgrupoRepository;
import org.acme.domain.service.GrupoService;
import org.acme.domain.service.SubgrupoService;
import org.acme.infra.tenant.TenantAware;

import java.util.List;
import java.util.UUID;

@TenantAware
@ApplicationScoped
public class SubgrupoServiceImpl implements SubgrupoService {

    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com nome = %s";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private SubgrupoRepository subgrupoRepository;
    private GrupoService grupoService;

    public SubgrupoServiceImpl(SubgrupoRepository subgrupoRepository, GrupoService grupoService){
        this.subgrupoRepository = subgrupoRepository;
        this.grupoService = grupoService;
    }

    @Override
    public Subgrupo inserirSubgrupo(SubgrupoRequest subgrupoRequest) {
        validaNomeSubgrupo(subgrupoRequest, null);
        Grupo grupo = null;
        if(subgrupoRequest.getGrupoId() != null){
            grupo = grupoService.buscarGrupoPorId(UUID.fromString(subgrupoRequest.getGrupoId()));
        }
        Subgrupo subgrupo = Subgrupo.builder()
                .nome(subgrupoRequest.getNome())
                .grupo(grupo)
                .build();

        subgrupoRepository.persist(subgrupo);
        return subgrupo;
    }

    private void validaNomeSubgrupo(SubgrupoRequest subgrupoRequest, UUID subgrupoId) {
        boolean fornecedorEncontrado = subgrupoRepository.find("upper(nome) = ?1 and (id != ?2 or ?2 is null)",
                        subgrupoRequest.getNome().toUpperCase(), subgrupoId)
                .list().isEmpty();
        if (!fornecedorEncontrado) {
            throw new RuntimeException(String.format(MSG_DUPLICADO,  subgrupoRequest.getNome()));
        }
    }

    @Override
    public Subgrupo atualizarSubgrupo(SubgrupoRequest subgrupoRequest, UUID id) {
        Subgrupo subgrupo = this.buscarSubgrupoPorId(id);
        Grupo grupo = grupoService.buscarGrupoPorId(UUID.fromString(subgrupoRequest.getGrupoId()));
        validaNomeSubgrupo(subgrupoRequest, id);
        try{
            subgrupo.setNome(subgrupoRequest.getNome());
            subgrupo.setGrupo(grupo);
            subgrupoRepository.persist(subgrupo);
        }catch (RuntimeException e){
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
        return subgrupo;
    }

    @Override
    public List<Subgrupo> listarSubgrupoFiltros(SubgrupoFilter subgrupoFilter, int page, int size) {
        return subgrupoRepository.paginacaoComFiltros(subgrupoFilter, page, size);
    }

    @Override
    public Subgrupo buscarSubgrupoPorId(UUID id) {
        return subgrupoRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));
    }

    @Override
    public void deletarSubgrupo(UUID id) {
        try{
            Subgrupo subgrupo = buscarSubgrupoPorId(id);
            subgrupoRepository.delete(subgrupo);
        }catch (RuntimeException e){
            throw new RuntimeException(String.format(ERRO_AO_DELETAR));
        }
    }
}
