package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.api.filter.PatrimonioFilter;
import org.acme.api.filter.TransacaoFilter;
import org.acme.api.request.PatrimonioRequest;
import org.acme.api.request.TransacaoRequest;
import org.acme.domain.enums.MoedaEnum;
import org.acme.domain.enums.TipoPatrimonioEnum;
import org.acme.domain.enums.TipoTransacaoEnum;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Patrimonio;
import org.acme.domain.model.Transacao;
import org.acme.domain.repository.PatrimonioRepository;
import org.acme.domain.repository.TransacaoRepository;
import org.acme.domain.service.ContaService;
import org.acme.domain.service.PatrimonioService;
import org.acme.domain.service.TransacaoService;
import org.acme.infra.tenant.TenantAware;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@TenantAware
@ApplicationScoped
public class TransacaoServiceImpl implements TransacaoService {

    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com essas especificações";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private TransacaoRepository transacaoRepository;
    private PatrimonioService patrimonioService;
    private ModelMapper modelMapper;

    @Inject
    public TransacaoServiceImpl(TransacaoRepository transacaoRepository, PatrimonioService patrimonioService, ModelMapper modelMapper) {
        this.transacaoRepository = transacaoRepository;
        this.patrimonioService = patrimonioService;
        this.modelMapper = modelMapper;
    }


    @Override
    public Transacao inserir(TransacaoRequest transacaoRequest) {
        try{
            Patrimonio patrimonio = patrimonioService.buscarPorId(UUID.fromString(transacaoRequest.getPatrimonio()));
            Transacao transacao = modelMapper.map(transacaoRequest, Transacao.class);
            transacao.setPatrimonio(patrimonio);
            transacao.setTipo(TipoTransacaoEnum.valueOf(transacaoRequest.getTipo()));
            calcularTransacao(transacao, patrimonio);
            transacaoRepository.persist(transacao);
            return transacao;
        }catch (Exception e){
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
    }

    @Override
    public Transacao atualizar(TransacaoRequest transacaoRequest, UUID id) {
        try {
            Transacao transacao = buscarPorId(id);
            Patrimonio patrimonio = patrimonioService.buscarPorId(UUID.fromString(transacaoRequest.getPatrimonio()));
            transacao.setPatrimonio(patrimonio);
            transacao.setTipo(TipoTransacaoEnum.valueOf(transacaoRequest.getTipo()));
            transacao.setDataTransacao(transacaoRequest.getDataTransacao());
            transacao.setValor(transacaoRequest.getValor());
            transacaoRepository.persist(transacao);
            return transacao;
        } catch (Exception e) {
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
    }

    @Override
    public List<Transacao> listarComFiltros(TransacaoFilter transacaoFilter, int page, int size) {
        return transacaoRepository.paginacaoComFiltros(transacaoFilter, page, size);
    }

    @Override
    public Transacao buscarPorId(UUID id) {
        return transacaoRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));
    }

    @Override
    public void deletar(UUID id) {
        try {
            Transacao transacao = buscarPorId(id);
            transacaoRepository.delete(transacao);
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ERRO_AO_DELETAR));
        }
    }

    public void calcularTransacao(Transacao transacao, Patrimonio patrimonio){
        PatrimonioRequest patrimonioRequest = modelMapper.map(patrimonio, PatrimonioRequest.class);

        if(transacao.getTipo() == TipoTransacaoEnum.ENTRADA){
            patrimonioRequest.setValor(patrimonio.getValor().add(transacao.getValor()));
        }else{
            patrimonioRequest.setValor(patrimonio.getValor().subtract(transacao.getValor()));
        }
        patrimonioService.atualizar(patrimonioRequest, patrimonio.getId());
    }
}
