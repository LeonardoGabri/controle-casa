package org.acme.domain.service;

import org.acme.api.dto.AcertoResponsavelDTO;
import org.acme.api.dto.ObrigacaoFinanceiraDTO;

import java.util.List;

public interface ResumoMensalService {
    List<AcertoResponsavelDTO> buscarAcertoResponsavel(String referenciaCobranca);
    List<ObrigacaoFinanceiraDTO> buscarObrigacaoFinanceira(String referenciaCobranca);

}
