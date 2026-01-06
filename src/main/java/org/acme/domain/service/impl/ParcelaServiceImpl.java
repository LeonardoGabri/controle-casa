package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.api.dto.ParcelaDTO;
import org.acme.api.filter.ParcelaFilter;
import org.acme.api.request.DespesaRequest;
import org.acme.api.request.ParcelaRequest;
import org.acme.api.request.PlanejamentoParcelasRequest;
import org.acme.domain.model.Despesa;
import org.acme.domain.model.Parcela;
import org.acme.domain.model.Responsavel;
import org.acme.domain.repository.ParcelaRepository;
import org.acme.domain.service.DespesaService;
import org.acme.domain.service.FornecedorService;
import org.acme.domain.service.ParcelaService;
import org.acme.domain.service.ResponsavelService;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class ParcelaServiceImpl implements ParcelaService {

    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com nome = %s";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_AO_PAGAR = "erro ao pagar registro";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private final String ERRO_NO_CALCULO_PORCENTAGEM = "A soma das porcentagens de divisão deve ser exatamente 100.";
    private final String ERRO_PLANEJAMENTO_PARCELAS = "Planejamento de parcelas é obrigatório";
    private ParcelaRepository parcelaRepository;
    private ResponsavelService responsavelService;
    private DespesaService despesaService;
    private FornecedorService fornecedorService;
    private ModelMapper modelMapper;

    @Inject
    public ParcelaServiceImpl(ParcelaRepository parcelaRepository, ResponsavelService responsavelService, DespesaService despesaService, FornecedorService fornecedorService, ModelMapper modelMapper) {
        this.parcelaRepository = parcelaRepository;
        this.responsavelService = responsavelService;
        this.despesaService = despesaService;
        this.fornecedorService = fornecedorService;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public Parcela inserirParcela(ParcelaRequest parcelaRequest) {
        try {
            Responsavel responsavel = responsavelService.buscarResponsavelPorId(UUID.fromString(parcelaRequest.getResponsavelId()));
            Despesa despesa = despesaService.buscarDespesaPorId(UUID.fromString(parcelaRequest.getDespesaId()));
            Parcela parcela = Parcela.builder()
                    .responsavel(responsavel)
                    .dataVencimento(parcelaRequest.getDataVencimento())
                    .valor(parcelaRequest.getValor())
                    .parcelaAtual(parcelaRequest.getParcelaAtual())
                    .porcentagemDivisao(parcelaRequest.getPorcentagemDivisao() != null ? parcelaRequest.getPorcentagemDivisao() : null)
                    .despesa(despesa)
                    .build();
            parcelaRepository.persist(parcela);
            return parcela;
        } catch (Exception e) {
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
    }

    @Override
    public Parcela atualizarParcela(ParcelaRequest parcelaRequest, UUID id) {
        Parcela parcela = buscarParcelaPorId(id);
        Responsavel responsavel = responsavelService.buscarResponsavelPorId(UUID.fromString(parcelaRequest.getResponsavelId()));
        Despesa despesa = despesaService.buscarDespesaPorId(UUID.fromString(parcelaRequest.getDespesaId()));
        try {
            parcela.setDataVencimento(parcelaRequest.getDataVencimento());
            parcela.setResponsavel(responsavel);
            parcela.setValor(parcelaRequest.getValor());
            parcela.setParcelaAtual(parcelaRequest.getParcelaAtual());
            parcela.setPorcentagemDivisao(parcelaRequest.getPorcentagemDivisao());
            parcela.setDespesa(despesa);

            parcelaRepository.persist(parcela);
        } catch (Exception e) {
            throw new RuntimeException(String.format(ERRO_AO_SALVAR, parcela));
        }

        return parcela;
    }

    @Override
    public List<Parcela> listar(ParcelaFilter parcelaFilter, int page, int size) {
        return parcelaRepository.paginacaoComFiltros(parcelaFilter, page, size);
    }

    @Override
    public Parcela buscarParcelaPorId(UUID id) {
        return parcelaRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));
    }

    public List<ParcelaDTO> calcularParcelas(DespesaRequest despesaRequest) {

        if (despesaRequest.getPlanejamentoParcelas() == null
                || despesaRequest.getPlanejamentoParcelas().isEmpty()) {
            throw new IllegalArgumentException(ERRO_PLANEJAMENTO_PARCELAS);
        }

        double somaPorcentagem = despesaRequest.getPlanejamentoParcelas()
                .stream()
                .mapToDouble(PlanejamentoParcelasRequest::getPorcentagemDivisao)
                .sum();

        if (Double.compare(somaPorcentagem, 100.0) != 0) {
            throw new IllegalArgumentException(ERRO_NO_CALCULO_PORCENTAGEM);
        }

        BigDecimal valorTotal = despesaRequest.getValorTotal();
        int numeroParcelas = despesaRequest.getNumeroParcelas();

        BigDecimal valorParcelaBase = valorTotal
                .divide(BigDecimal.valueOf(numeroParcelas), 4, BigDecimal.ROUND_HALF_UP)
                .setScale(2, BigDecimal.ROUND_HALF_UP);

        YearMonth yearMonth = YearMonth.parse(
                despesaRequest.getReferenciaCobranca(),
                DateTimeFormatter.ofPattern("MM/yyyy")
        );

        LocalDate dataBase = yearMonth.atDay(1);

        List<ParcelaDTO> parcelas = new ArrayList<>();

        for (int i = 1; i <= numeroParcelas; i++) {

            LocalDate vencimento = dataBase.plusMonths(i - 1);

            for (PlanejamentoParcelasRequest planejamento : despesaRequest.getPlanejamentoParcelas()) {

                BigDecimal percentual = BigDecimal
                        .valueOf(planejamento.getPorcentagemDivisao())
                        .divide(BigDecimal.valueOf(100), 4, BigDecimal.ROUND_HALF_UP);

                BigDecimal valorResponsavel = valorParcelaBase
                        .multiply(percentual)
                        .setScale(2, BigDecimal.ROUND_HALF_UP);

                parcelas.add(
                        ParcelaDTO.builder()
                                .dataVencimento(vencimento)
                                .responsavelId(planejamento.getResponsavelId())
                                .responsavelNome(
                                        responsavelService
                                                .buscarResponsavelPorId(UUID.fromString(planejamento.getResponsavelId()))
                                                .getNome()
                                )
                                .porcentagemDivisao(planejamento.getPorcentagemDivisao())
                                .parcelaAtual(String.valueOf(i))
                                .valor(valorResponsavel)
                                .fornecedorId(despesaRequest.getFornecedorId())
                                .fornecedorNome(
                                        fornecedorService
                                                .buscarFornecedorPorId(UUID.fromString(despesaRequest.getFornecedorId()))
                                                .getNome()
                                )
                                .build()
                );
            }
        }

        return parcelas;
    }


    public void validarPorcentagemPlanejamento(List<PlanejamentoParcelasRequest> planejamentoParcelasRequests) {

        if (planejamentoParcelasRequests == null || planejamentoParcelasRequests.isEmpty()) {
            throw new IllegalArgumentException(ERRO_PLANEJAMENTO_PARCELAS);
        }

        BigDecimal somaPorcentagem = planejamentoParcelasRequests.stream()
                .map(PlanejamentoParcelasRequest::getPorcentagemDivisao)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (somaPorcentagem.compareTo(BigDecimal.valueOf(100)) != 0) {
            throw new IllegalArgumentException(ERRO_NO_CALCULO_PORCENTAGEM);
        }
    }
}
