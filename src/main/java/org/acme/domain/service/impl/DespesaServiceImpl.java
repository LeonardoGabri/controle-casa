package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.api.request.DespesaRequest;
import org.acme.domain.enums.SituacaoEnum;
import org.acme.domain.model.*;
import org.acme.domain.repository.DespesaRepository;
import org.acme.domain.service.*;

import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class DespesaServiceImpl implements DespesaService {

    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com nome = %s";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private DespesaRepository despesaRespository;
    private ParcelaService parcelaService;
    private FornecedorService fornecedorService;
    private GrupoService grupoService;
    private BancoService bancoService;



    @Inject
    public DespesaServiceImpl(
            DespesaRepository despesaRespository,
            ParcelaService parcelaService,
            BancoService bancoService,
            FornecedorService fornecedorService,
            GrupoService grupoService
    ){
        this.despesaRespository = despesaRespository;
        this.parcelaService = parcelaService;
        this.bancoService = bancoService;
        this.fornecedorService = fornecedorService;
        this.grupoService = grupoService;
    }


    @Override
    @Transactional
    public Despesa inserirDespesa(DespesaRequest despesaRequest) {
        Banco banco = bancoService.buscarBancoPorId(UUID.fromString(despesaRequest.getBancoId()));
        Fornecedor fornecedor = fornecedorService.buscarFornecedorPorId(UUID.fromString(despesaRequest.getFornecedorId()));
        Grupo grupo = grupoService.buscarGrupoPorId(UUID.fromString(despesaRequest.getGrupoId()));

        Despesa despesa = Despesa.builder()
                .banco(banco)
                .fornecedor(fornecedor)
                .grupo(grupo)
                .anoInicioCobranca(despesaRequest.getAnoInicioCobranca())
                .mesInicioCobranca(despesaRequest.getMesInicioCobranca())
                .nParcelas(despesaRequest.getNumeroParcelas())
                .valorTotal(despesaRequest.getValorTotal())
                .valorTotalAtivo(despesaRequest.getValorTotal())
                .situacao(SituacaoEnum.ABERTA)
                .build();
        despesaRespository.persist(despesa);

        List<Parcela> parcelasCalculadas = this.parcelaService.calcularParcelas(despesa, despesaRequest.getPlanejamentoParcelas());
        despesa.setParcelas(parcelasCalculadas);
        return despesa;
    }

    @Override
    public Despesa atualizarDespesa() {
        return null;
    }

    @Override
    public List<Despesa> listar() {
        return null;
    }

    @Override
    public Despesa buscarDespesaPorId(UUID id) {
        return null;
    }
}
