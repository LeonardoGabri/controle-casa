package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.api.filter.PatrimonioFilter;
import org.acme.api.request.PatrimonioRequest;
import org.acme.domain.enums.MoedaEnum;
import org.acme.domain.enums.TipoPatrimonioEnum;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Patrimonio;
import org.acme.domain.repository.PatrimonioRepository;
import org.acme.domain.service.ContaService;
import org.acme.domain.service.PatrimonioService;
import org.acme.infra.tenant.TenantAware;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

@TenantAware
@ApplicationScoped
public class PatrimonioServiceImpl implements PatrimonioService {

    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com essas especificações";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private PatrimonioRepository patrimonioRepository;
    private ContaService contaService;
    private ModelMapper modelMapper;

    @Inject
    public PatrimonioServiceImpl(PatrimonioRepository patrimonioRepository, ContaService contaService, ModelMapper modelMapper) {
        this.patrimonioRepository = patrimonioRepository;
        this.contaService = contaService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Patrimonio inserir(PatrimonioRequest patrimonioRequest) {
        try {
            Conta conta = contaService.buscarContaPorId(UUID.fromString(patrimonioRequest.getConta()));
            Patrimonio patrimonio = modelMapper.map(patrimonioRequest, Patrimonio.class);
            patrimonio.setTipo(TipoPatrimonioEnum.valueOf(patrimonioRequest.getTipo()));
            if(patrimonioRequest.getMoeda() != null){
                patrimonio.setMoeda(MoedaEnum.valueOf(patrimonioRequest.getMoeda()));
            }
            patrimonio.setConta(conta);
            patrimonioRepository.persist(patrimonio);
            return patrimonio;
        } catch (Exception e) {
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
    }

    @Override
    public Patrimonio atualizar(PatrimonioRequest patrimonioRequest, UUID id) {
        try {
            Patrimonio patrimonio = buscarPorId(id);
            Conta conta = contaService.buscarContaPorId(UUID.fromString(patrimonioRequest.getConta()));
            patrimonio.setConta(conta);
            patrimonio.setTipo(TipoPatrimonioEnum.valueOf(patrimonioRequest.getTipo()));
            if(patrimonioRequest.getMoeda() != null){
                patrimonio.setMoeda(MoedaEnum.valueOf(patrimonioRequest.getMoeda()));
            }
            patrimonio.setValor(patrimonioRequest.getValor());
            patrimonio.setDescricao(patrimonioRequest.getDescricao());
            patrimonio.setDataInicio(patrimonioRequest.getDataInicio());
            patrimonio.setDataFim(patrimonioRequest.getDataFim());
            patrimonioRepository.persist(patrimonio);
            return patrimonio;
        } catch (Exception e) {
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
    }

    @Override
    public List<Patrimonio> listarComFiltros(PatrimonioFilter patrimonioFilter, int page, int size) {
        return patrimonioRepository.paginacaoComFiltros(patrimonioFilter, page, size);
    }

    @Override
    public Patrimonio buscarPorId(UUID id) {
        return patrimonioRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));
    }

    @Override
    public void deletar(UUID id) {
        try {
            Patrimonio patrimonio = buscarPorId(id);
            patrimonioRepository.delete(patrimonio);
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ERRO_AO_DELETAR));
        }
    }
}
