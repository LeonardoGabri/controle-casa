package org.acme.domain.service;

import org.acme.api.dto.ParcelaDTO;
import org.acme.api.request.PlanejamentoParcelasRequest;
import org.acme.domain.model.Despesa;
import org.acme.domain.model.Parcela;

import java.util.List;
import java.util.UUID;

public interface ParcelaService {
    public ParcelaDTO inserirParcela(Parcela parcela);
    public ParcelaDTO atualizarParcela();
    public List<ParcelaDTO> listar();
    public Parcela buscarParcelaPorId(UUID id);
    public List<Parcela> calcularParcelas(Despesa despesaDTO, List<PlanejamentoParcelasRequest> planejamentoParcelasRequests);

}
