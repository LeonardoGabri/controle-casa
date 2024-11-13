package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.api.filter.DespesaFilter;
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
    private final String ERRO_AO_PAGAR = "erro ao pagar despesa";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private DespesaRepository despesaRespository;
    private ParcelaService parcelaService;
    private FornecedorService fornecedorService;
    private SubgrupoService subgrupoService;
    private ContaService contaService;



    @Inject
    public DespesaServiceImpl(
            DespesaRepository despesaRespository,
            ParcelaService parcelaService,
            ContaService contaService,
            FornecedorService fornecedorService,
            SubgrupoService subgrupoService
    ){
        this.despesaRespository = despesaRespository;
        this.parcelaService = parcelaService;
        this.contaService = contaService;
        this.fornecedorService = fornecedorService;
        this.subgrupoService = subgrupoService;
    }


    @Override
    @Transactional
    public Despesa inserirDespesa(DespesaRequest despesaRequest) {
        Conta conta = contaService.buscarContaPorId(UUID.fromString(despesaRequest.getContaId()));
        Fornecedor fornecedor = fornecedorService.buscarFornecedorPorId(UUID.fromString(despesaRequest.getFornecedorId()));
        Subgrupo subgrupo = subgrupoService.buscarSubgrupoPorId(UUID.fromString(despesaRequest.getSubgrupoId()));

        Despesa despesa = Despesa.builder()
                .conta(conta)
                .fornecedor(fornecedor)
                .subgrupo(subgrupo)
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
    public Despesa atualizarDespesa(DespesaRequest despesaRequest, UUID id) {
        deletarDespesa(id);
        return inserirDespesa(despesaRequest);
    }

    @Override
    public List<Despesa> listar(DespesaFilter despesaFilter, int page, int size) {
        return despesaRespository.paginacaoComFiltros(despesaFilter, page, size);
    }

    @Override
    public Despesa buscarDespesaPorId(UUID id) {
        return despesaRespository.findById(id).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO)));
    }

    @Override
    public void deletarDespesa(UUID id) {
        Despesa despesa = buscarDespesaPorId(id);
        despesaRespository.delete(despesa);
    }

    @Override
    public Despesa pagarDespesa(UUID id) {
        try{
            Despesa despesa = buscarDespesaPorId(id);
            despesa.setSituacao(SituacaoEnum.PAGO);
            despesaRespository.persist(despesa);
            return despesa;
        }catch (Exception e){
            throw new RuntimeException(String.format(ERRO_AO_PAGAR));
        }
    }
}
