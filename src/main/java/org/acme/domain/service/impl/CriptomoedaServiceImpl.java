package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.api.filter.ContaFilter;
import org.acme.api.filter.GrupoFilter;
import org.acme.api.request.ContaRequest;
import org.acme.api.request.CriptomoedaRequest;
import org.acme.api.request.GrupoRequest;
import org.acme.domain.model.Banco;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Criptomoeda;
import org.acme.domain.model.Responsavel;
import org.acme.domain.repository.ContaRepository;
import org.acme.domain.repository.CriptomoedaRepository;
import org.acme.domain.repository.PatrimonioRepository;
import org.acme.domain.service.BancoService;
import org.acme.domain.service.ContaService;
import org.acme.domain.service.CriptomoedaService;
import org.acme.domain.service.ResponsavelService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class CriptomoedaServiceImpl implements CriptomoedaService {
    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com essas especificações";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private final PatrimonioRepository patrimonioRepository;
    private CriptomoedaRepository criptomoedaRepository;
    private ModelMapper modelMapper;

    @Inject
    public CriptomoedaServiceImpl(CriptomoedaRepository criptomoedaRepository, ModelMapper modelMapper, PatrimonioRepository patrimonioRepository){
        this.criptomoedaRepository = criptomoedaRepository;
        this.modelMapper = modelMapper;
        this.patrimonioRepository = patrimonioRepository;
    };


    @Override
    public Criptomoeda inserir(CriptomoedaRequest request ) {
        try{
            Criptomoeda criptomoeda = modelMapper.map(request, Criptomoeda.class);
            criptomoedaRepository.persist(criptomoeda);
            return criptomoeda;
        }catch (Exception e){
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
    }

    @Override
    public Criptomoeda atualizar(CriptomoedaRequest request, UUID id) {
        Criptomoeda criptomoeda = buscarPorId(id);
        try {
            criptomoeda.setValor(request.getValor());
            criptomoedaRepository.persist(criptomoeda);
            return criptomoeda;
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
    }

    @Override
    public List<Criptomoeda> listar() {
        return criptomoedaRepository.listAll();
    }

    @Override
    public Criptomoeda buscarPorId(UUID id) {
        Criptomoeda criptomoeda = criptomoedaRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));
        return criptomoeda;
    }

    @Override
    public Criptomoeda buscarPorCodigo(String codigo) {
        List<Criptomoeda> criptos = listar();
        return criptos.stream().filter(c -> c.getSimbolo().equals(codigo)).findFirst().orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, codigo)));
    }

    @Override
    public void deletar(UUID id) {
        try {
            Criptomoeda criptomoeda = buscarPorId(id);
            criptomoedaRepository.delete(criptomoeda);
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ERRO_AO_DELETAR));
        }
    }
}
