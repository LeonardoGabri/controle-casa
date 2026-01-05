package org.acme.domain.service;

import org.acme.api.filter.DespesaFilter;
import org.acme.api.request.DespesaRequest;
import org.acme.api.request.PlanejamentoParcelasRequest;
import org.acme.domain.model.Despesa;
import org.acme.domain.model.PlanejamentoParcelas;

import java.util.List;
import java.util.UUID;

public interface PlanejamentoParcelasService {
    public PlanejamentoParcelas inserirPlanejamentoParcelas(PlanejamentoParcelasRequest planejamentoParcelas);
    public PlanejamentoParcelas atualizarPlanejamentoParcelas(PlanejamentoParcelasRequest planejamentoParcelas, UUID id);
    public PlanejamentoParcelas buscarPlanejamentoParcelasPorId(UUID id);
    public void deletarPlanejamentoParcelas(UUID id);
}
