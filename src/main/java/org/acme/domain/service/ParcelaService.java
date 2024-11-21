package org.acme.domain.service;

import org.acme.api.dto.ValoresDTO;
import org.acme.api.filter.ParcelaFilter;
import org.acme.api.request.ParcelaRequest;
import org.acme.api.request.PlanejamentoParcelasRequest;
import org.acme.domain.model.Despesa;
import org.acme.domain.model.Parcela;

import java.util.List;
import java.util.UUID;

public interface ParcelaService {
    public Parcela inserirParcela(ParcelaRequest parcela);
    public Parcela atualizarParcela(ParcelaRequest parcelaRequest, UUID id);
    public List<Parcela> listar(ParcelaFilter parcelaFilter, int page, int size);
    public Parcela buscarParcelaPorId(UUID id);
    public List<Parcela> calcularParcelas(Despesa despesaDTO, List<PlanejamentoParcelasRequest> planejamentoParcelasRequests);
}
