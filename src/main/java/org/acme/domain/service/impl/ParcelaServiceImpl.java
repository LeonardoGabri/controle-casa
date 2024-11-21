package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.api.dto.ValoresDTO;
import org.acme.api.filter.ParcelaFilter;
import org.acme.api.request.ParcelaRequest;
import org.acme.api.request.PlanejamentoParcelasRequest;
import org.acme.domain.enums.SituacaoEnum;
import org.acme.domain.model.Despesa;
import org.acme.domain.model.Parcela;
import org.acme.domain.model.Responsavel;
import org.acme.domain.repository.ParcelaRepository;
import org.acme.domain.service.DespesaService;
import org.acme.domain.service.ParcelaService;
import org.acme.domain.service.ResponsavelService;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private ParcelaRepository parcelaRepository;
    private ResponsavelService responsavelService;
    private DespesaService despesaService;

    @Inject
    public ParcelaServiceImpl(ParcelaRepository parcelaRepository, ResponsavelService responsavelService, DespesaService despesaService){
        this.parcelaRepository = parcelaRepository;
        this.responsavelService = responsavelService;
        this.despesaService = despesaService;
    }


    @Override
    @Transactional
    public Parcela inserirParcela(ParcelaRequest parcelaRequest) {
        try{
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
        }catch (Exception e){
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
    }

    @Override
    public Parcela atualizarParcela(ParcelaRequest parcelaRequest, UUID id){
        Parcela parcela = buscarParcelaPorId(id);
        Responsavel responsavel = responsavelService.buscarResponsavelPorId(UUID.fromString(parcelaRequest.getResponsavelId()));
        Despesa despesa = despesaService.buscarDespesaPorId(UUID.fromString(parcelaRequest.getDespesaId()));
        try{
            parcela.setDataVencimento(parcelaRequest.getDataVencimento());
            parcela.setResponsavel(responsavel);
            parcela.setValor(parcelaRequest.getValor());
            parcela.setParcelaAtual(parcelaRequest.getParcelaAtual());
            parcela.setPorcentagemDivisao(parcelaRequest.getPorcentagemDivisao());
            parcela.setDespesa(despesa);

            parcelaRepository.persist(parcela);
        }catch (Exception e){
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
        return parcelaRepository.findById(id).orElseThrow(()->new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));
    }

    @Override
    public List<Parcela> calcularParcelas(Despesa despesa, List<PlanejamentoParcelasRequest> planejamentoParcelasRequests) {
        List<Parcela> parcelas = new ArrayList<>();

        String[] referencia = despesa.getReferenciaCobranca().split("/");
        int mesInicio = Integer.parseInt(referencia[0]);
        int anoInicio = Integer.parseInt(referencia[1]);

        LocalDate dataInicialVencimento = LocalDate.of(anoInicio, mesInicio, 1);

        planejamentoParcelasRequests.forEach(planejamentoParcelasRequest -> {
            BigDecimal valorTotal = despesa.getValorTotal();
            BigDecimal porcentagemDivisao = BigDecimal.valueOf(planejamentoParcelasRequest.getPorcentagemDivisao());
            BigDecimal valorParcela = valorTotal.multiply(porcentagemDivisao.divide(BigDecimal.valueOf(100)));

            valorParcela = valorParcela.divide(BigDecimal.valueOf(despesa.getNumeroParcelas()), 2, BigDecimal.ROUND_UP);

            planejamentoParcelasRequest.setValor(valorParcela);

            for (int i = 0; i < despesa.getNumeroParcelas(); i++) {
                LocalDate dataVencimentoParcela = dataInicialVencimento.plusMonths(i);
                String parcelaAtual = (i + 1) + "/" + despesa.getNumeroParcelas();

                ParcelaRequest parcelaCalculada = ParcelaRequest.builder()
                        .valor(valorParcela)
                        .dataVencimento(dataVencimentoParcela)
                        .despesaId(despesa.getId().toString())
                        .porcentagemDivisao(planejamentoParcelasRequest.getPorcentagemDivisao())
                        .responsavelId(planejamentoParcelasRequest.getResponsavelId())
                        .parcelaAtual(parcelaAtual)
                        .build();

                parcelas.add(inserirParcela(parcelaCalculada));
            }
        });

        return parcelas;
    }

    @Override
    public ValoresDTO buscarValoresResponsavel(UUID responsavelId) {
        return parcelaRepository.findValoresByResponsavelId(responsavelId);
    }

}
