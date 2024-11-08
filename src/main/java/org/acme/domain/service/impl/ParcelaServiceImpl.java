package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.api.dto.ParcelaDTO;
import org.acme.api.request.PlanejamentoParcelasRequest;
import org.acme.domain.enums.SituacaoEnum;
import org.acme.domain.model.Despesa;
import org.acme.domain.model.Parcela;
import org.acme.domain.model.Responsavel;
import org.acme.domain.repository.ParcelaRepository;
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
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private ParcelaRepository parcelaRepository;
    private ResponsavelService responsavelService;

    @Inject
    public ParcelaServiceImpl(ParcelaRepository parcelaRepository, ResponsavelService responsavelService){
        this.parcelaRepository = parcelaRepository;
        this.responsavelService = responsavelService;
    }


    @Override
    @Transactional
    public ParcelaDTO inserirParcela(Parcela parcela) {
        parcelaRepository.persist(parcela);
        return ParcelaDTO.entityFromDTO(parcela);
    }

    @Override
    public ParcelaDTO atualizarParcela() {
        return null;
    }

    @Override
    public List<ParcelaDTO> listar() {
        return null;
    }

    @Override
    public Parcela buscarParcelaPorId(UUID id) {
        return null;
    }

    @Override
    public List<Parcela> calcularParcelas(Despesa despesa, List<PlanejamentoParcelasRequest> planejamentoParcelasRequests) {
        List<Parcela> parcelas = new ArrayList<>();

        LocalDate dataInicialVencimento = LocalDate.of(despesa.getAnoInicioCobranca(), despesa.getMesInicioCobranca(), 1);

        planejamentoParcelasRequests.forEach(planejamentoParcelasRequest -> {
            Responsavel responsavel = responsavelService.buscarResponsavelPorId(UUID.fromString(planejamentoParcelasRequest.getResponsavelId()));
            BigDecimal valorTotal = despesa.getValorTotal();
            BigDecimal porcentagemDivisao = BigDecimal.valueOf(planejamentoParcelasRequest.getPorcentagemDivisao());
            BigDecimal valorParcela = valorTotal.multiply(porcentagemDivisao.divide(BigDecimal.valueOf(100)));

            valorParcela = valorParcela.divide(BigDecimal.valueOf(despesa.getNParcelas()), BigDecimal.ROUND_UP);

            planejamentoParcelasRequest.setValor(valorParcela);

            for (int i = 0; i < despesa.getNParcelas(); i++) {
                LocalDate dataVencimentoParcela = dataInicialVencimento.plusMonths(i);

                Parcela parcelaCalculada = Parcela.builder()
                        .valor(valorParcela)
                        .situacao(SituacaoEnum.ABERTA)
                        .dataVencimento(dataVencimentoParcela)
                        .despesa(despesa)
                        .porcetagemDivisao(planejamentoParcelasRequest.getPorcentagemDivisao())
                        .responsavel(responsavel)
                        .build();

                inserirParcela(parcelaCalculada);
                parcelas.add(parcelaCalculada);
            }
        });

        return parcelas;
    }
}
