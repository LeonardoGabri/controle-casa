package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.api.dto.ResumoDespesaPorContaDTO;
import org.acme.api.filter.DespesaFilter;
import org.acme.api.request.DespesaRequest;
import org.acme.api.request.ParcelaRequest;
import org.acme.api.request.PlanejamentoParcelasRequest;
import org.acme.domain.model.*;
import org.acme.domain.repository.DespesaRepository;
import org.acme.domain.service.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@ApplicationScoped
public class DespesaServiceImpl implements DespesaService {

    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com nome = %s";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_AO_PAGAR = "erro ao pagar despesa";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private final String ERRO_PORCENTAGEM_DIVISAO = "Porcentagem de divisão é obrigatória no planejamento de parcelas";
    private DespesaRepository despesaRespository;
    private ParcelaService parcelaService;
    private FornecedorService fornecedorService;
    private SubgrupoService subgrupoService;
    private ContaService contaService;
    private PlanejamentoParcelasService planejamentoParcelasService;

    @Inject
    public DespesaServiceImpl(
            DespesaRepository despesaRespository,
            ParcelaService parcelaService,
            ContaService contaService,
            FornecedorService fornecedorService,
            SubgrupoService subgrupoService,
            PlanejamentoParcelasService planejamentoParcelasService
    ){
        this.despesaRespository = despesaRespository;
        this.parcelaService = parcelaService;
        this.contaService = contaService;
        this.fornecedorService = fornecedorService;
        this.subgrupoService = subgrupoService;
        this.planejamentoParcelasService = planejamentoParcelasService;
    }


    @Override
    @Transactional
    public Despesa inserirDespesa(DespesaRequest despesaRequest) {
        validarReferenciaCobranca(despesaRequest.getReferenciaCobranca());
        Conta conta = contaService.buscarContaPorId(UUID.fromString(despesaRequest.getContaId()));
        Fornecedor fornecedor = fornecedorService.buscarFornecedorPorId(UUID.fromString(despesaRequest.getFornecedorId()));

        Subgrupo subgrupo = null;
        if (despesaRequest.getSubgrupoId() != null) {
            subgrupo = subgrupoService.buscarSubgrupoPorId(UUID.fromString(despesaRequest.getSubgrupoId()));
        }

        parcelaService.validarPorcentagemPlanejamento(despesaRequest.getPlanejamentoParcelas());

        Despesa despesa = Despesa.builder()
                .conta(conta)
                .fornecedor(fornecedor)
                .subgrupo(subgrupo)
                .descricao(despesaRequest.getDescricao())
                .dataLancamento(despesaRequest.getDataLancamento())
                .referenciaCobranca(despesaRequest.getReferenciaCobranca())
                .numeroParcelas(despesaRequest.getNumeroParcelas())
                .valorTotal(despesaRequest.getValorTotal())
                .valorTotalAtivo(despesaRequest.getValorTotal())
                .build();

        despesa.setPlanejamentoParcelas(
                getPlanejamentoParcelas(despesaRequest.getPlanejamentoParcelas(), despesa)
        );

        despesa.setParcelas(
                getParcelas(despesaRequest.getParcelas(), despesa)
        );

        despesaRespository.persist(despesa);

        return despesa;
    }

    private List<PlanejamentoParcelas> getPlanejamentoParcelas(
            List<PlanejamentoParcelasRequest> planejamentoParcelasRequests,
            Despesa despesa
    ) {
        return planejamentoParcelasRequests.stream()
                .map(item -> {

                    if (item.getPorcentagemDivisao() == null) {
                        throw new IllegalArgumentException(
                                ERRO_PORCENTAGEM_DIVISAO
                        );
                    }

                    PlanejamentoParcelas pp = PlanejamentoParcelas.builder()
                            .porcentagemDivisao(item.getPorcentagemDivisao())
                            .responsavel(
                                    Responsavel.builder()
                                            .id(UUID.fromString(item.getResponsavelId()))
                                            .build()
                            )
                            .despesa(despesa)
                            .build();

                    return pp;
                })
                .collect(Collectors.toList());
    }

    private List<Parcela> getParcelas(List<ParcelaRequest> parcelasRequest, Despesa despesa) {
        return parcelasRequest
                .stream()
                .map(item -> Parcela.builder()
                        .despesa(despesa)
                        .responsavel(
                                Responsavel.builder()
                                        .id(UUID.fromString(item.getResponsavelId()))
                                        .build()
                        )
                        .dataVencimento(item.getDataVencimento())
                        .valor(item.getValor())
                        .porcentagemDivisao(item.getPorcentagemDivisao())
                        .parcelaAtual(item.getParcelaAtual())
                        .build()
                )
                .toList();
    }

    @Override
    public Despesa atualizarDespesa(DespesaRequest despesaRequest, UUID id) {
        try {
            if (despesaRequest.getPlanejamentoParcelas() != null) {
                deletarDespesa(id);
                return inserirDespesa(despesaRequest);
            } else {
                Conta conta = contaService.buscarContaPorId(UUID.fromString(despesaRequest.getContaId()));
                Fornecedor fornecedor = fornecedorService.buscarFornecedorPorId(UUID.fromString(despesaRequest.getFornecedorId()));
                Subgrupo subgrupo = subgrupoService.buscarSubgrupoPorId(UUID.fromString(despesaRequest.getSubgrupoId()));
                Despesa despesa = buscarDespesaPorId(id);

                despesa.setConta(conta);
                despesa.setFornecedor(fornecedor);
                despesa.setSubgrupo(subgrupo);
                despesa.setDescricao(despesaRequest.getDescricao());
                despesa.setDataLancamento(despesaRequest.getDataLancamento());
                despesa.setReferenciaCobranca(despesaRequest.getReferenciaCobranca());
                despesa.setNumeroParcelas(despesaRequest.getNumeroParcelas());
                despesa.setValorTotal(despesaRequest.getValorTotal());

                despesaRespository.persist(despesa);
                return despesa;
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
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

    public List<ResumoDespesaPorContaDTO> buscarResumoPorConta(){
        return despesaRespository.buscarResumoPorConta();
    }

    private void validarReferenciaCobranca(String referenciaCobranca) {
        String regex = "^(0[1-9]|1[0-2])/\\d{4}$";
        if (referenciaCobranca == null || !referenciaCobranca.matches(regex)) {
            throw new IllegalArgumentException("O campo referenciaCobranca deve estar no formato MM/AAAA.");
        }
    }
}
