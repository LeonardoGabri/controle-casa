package org.acme.domain.service;

import org.acme.api.dto.ParcelaDTO;
import org.acme.api.dto.PlanejamentoParcelasDTO;
import org.acme.api.request.PlanejamentoParcelasRequest;
import org.acme.domain.model.Parcela;
import org.acme.domain.model.PlanejamentoParcelas;

import java.util.List;
import java.util.UUID;

public interface PlanejamentoParcelasService {
    public PlanejamentoParcelas inserirPlanejamentoParcelas(PlanejamentoParcelasRequest planejamentoParcelas);
    public PlanejamentoParcelas atualizarPlanejamentoParcelas(PlanejamentoParcelasRequest planejamentoParcelas, UUID id);
    public PlanejamentoParcelas buscarPlanejamentoParcelasPorId(UUID id);
    public void deletarPlanejamentoParcelas(UUID id);
    public List<PlanejamentoParcelasDTO> criarPlanejamentoParcelas(List<ParcelaDTO> parcelas);
}
