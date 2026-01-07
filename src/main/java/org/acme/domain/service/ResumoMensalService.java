package org.acme.domain.service;

import org.acme.api.dto.BancoDTO;
import org.acme.api.dto.ResumoMensalDTO;
import org.acme.domain.model.Banco;

import java.util.List;
import java.util.UUID;

public interface ResumoMensalService {
    List<ResumoMensalDTO> buscarResumoMensal(String referenciaCobranca);

}
