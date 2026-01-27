package org.acme.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.api.dto.ParcelaDTO;
import org.acme.api.dto.PlanejamentoParcelasDTO;
import org.acme.api.filter.DespesaFilter;
import org.acme.api.request.DespesaRequest;
import org.acme.api.request.PlanejamentoParcelasRequest;
import org.acme.domain.model.*;
import org.acme.domain.repository.DespesaRepository;
import org.acme.domain.repository.PlanejamentoParcelasRepository;
import org.acme.domain.service.*;
import org.acme.infra.tenant.TenantAware;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@TenantAware
@ApplicationScoped
public class PlanejamentoParcelasServiceImpl implements PlanejamentoParcelasService {

    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String MSG_DUPLICADO = "Já cadastrado registro com nome = %s";
    private final String ERRO_AO_SALVAR = "erro ao salvar registro";
    private final String ERRO_NO_CALCULO_PORCENTAGEM = "A soma das porcentagens de divisão deve ser exatamente 100.";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";
    private  PlanejamentoParcelasRepository planejamentoParcelasRepository;
    private  ResponsavelService responsavelService;
    private  DespesaService despesaService;


    @Inject
    public PlanejamentoParcelasServiceImpl(
            PlanejamentoParcelasRepository planejamentoParcelasRepository,
            ResponsavelService responsavelService,
            DespesaService despesaService

    ){
        this.planejamentoParcelasRepository = planejamentoParcelasRepository;
        this.responsavelService = responsavelService;
        this.despesaService = despesaService;
    }

    @Override
    public PlanejamentoParcelas inserirPlanejamentoParcelas(PlanejamentoParcelasRequest planejamentoParcelasRequest) {
        try{
            Responsavel responsavel = this.responsavelService.buscarResponsavelPorId(UUID.fromString(planejamentoParcelasRequest.getResponsavelId()));
            Despesa despesa = this.despesaService.buscarDespesaPorId(UUID.fromString(planejamentoParcelasRequest.getDespesaId()));

            PlanejamentoParcelas planejamentoParcelas = PlanejamentoParcelas.builder()
                    .responsavel(responsavel)
                    .porcentagemDivisao(planejamentoParcelasRequest.getPorcentagemDivisao())
                    .despesa(despesa)
                    .build();

            planejamentoParcelasRepository.persist(planejamentoParcelas);
            return planejamentoParcelas;
        }catch (Exception e){
            throw new RuntimeException(String.format(ERRO_AO_SALVAR));
        }
    }

    @Override
    public PlanejamentoParcelas atualizarPlanejamentoParcelas(PlanejamentoParcelasRequest planejamentoParcelasRequest, UUID id) {
        PlanejamentoParcelas planejamentoParcelas = buscarPlanejamentoParcelasPorId(id);
        Responsavel responsavel = responsavelService.buscarResponsavelPorId(UUID.fromString(planejamentoParcelasRequest.getResponsavelId()));
        Despesa despesa = this.despesaService.buscarDespesaPorId(UUID.fromString(planejamentoParcelasRequest.getDespesaId()));
        try{
            planejamentoParcelas.setResponsavel(responsavel);
            planejamentoParcelas.setPorcentagemDivisao(planejamentoParcelasRequest.getPorcentagemDivisao());
            planejamentoParcelas.setDespesa(despesa);

            planejamentoParcelasRepository.persist(planejamentoParcelas);
        }catch (Exception e){
            throw new RuntimeException(String.format(ERRO_AO_SALVAR, planejamentoParcelasRequest));
        }
        return planejamentoParcelas;
    }

    @Override
    public PlanejamentoParcelas buscarPlanejamentoParcelasPorId(UUID id) {
        return planejamentoParcelasRepository.findById(id).orElseThrow(()->new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));
    }

    @Override
    public void deletarPlanejamentoParcelas(UUID id) {
        try {
            PlanejamentoParcelas planejamentoParcelas = buscarPlanejamentoParcelasPorId(id);
            planejamentoParcelasRepository.delete(planejamentoParcelas);
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ERRO_AO_DELETAR));
        }
    }

    @Override
    public List<PlanejamentoParcelasDTO> criarPlanejamentoParcelas(List<ParcelaDTO> parcelas) {

        // 1. Agrupa e soma valores por responsável (usando ID)
        Map<String, BigDecimal> totalPorResponsavel =
                parcelas.stream()
                        .filter(p -> p.getValor() != null)
                        .collect(Collectors.groupingBy(
                                ParcelaDTO::getResponsavelId,
                                Collectors.reducing(
                                        BigDecimal.ZERO,
                                        ParcelaDTO::getValor,
                                        BigDecimal::add
                                )
                        ));

        // 2. Total geral
        BigDecimal totalGeral = totalPorResponsavel.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3. Nome do responsável (map auxiliar)
        Map<String, String> nomePorResponsavel =
                parcelas.stream()
                        .collect(Collectors.toMap(
                                ParcelaDTO::getResponsavelId,
                                ParcelaDTO::getResponsavelNome,
                                (nome1, nome2) -> nome1 // mantém o primeiro
                        ));

        // 4. Monta o preview do planejamento
        return totalPorResponsavel.entrySet()
                .stream()
                .map(entry -> {
                    String responsavelId = entry.getKey();
                    BigDecimal total = entry.getValue();

                    double percentual = 0.0;
                    if (totalGeral.compareTo(BigDecimal.ZERO) > 0) {
                        percentual = total
                                .multiply(BigDecimal.valueOf(100))
                                .divide(totalGeral, 2, RoundingMode.HALF_UP)
                                .doubleValue();
                    }

                    return PlanejamentoParcelasDTO.builder()
                            .id(UUID.randomUUID()) // apenas para o preview
                            .responsavelId(responsavelId)
                            .responsavelNome(nomePorResponsavel.get(responsavelId))
                            .porcentagemDivisao(percentual)
                            .build();
                })
                .sorted(Comparator.comparing(PlanejamentoParcelasDTO::getResponsavelNome))
                .toList();
    }


}
