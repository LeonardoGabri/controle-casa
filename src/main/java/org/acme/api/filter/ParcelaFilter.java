package org.acme.api.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Data;
import org.acme.domain.enums.SituacaoEnum;

@Data
public class ParcelaFilter {
    @QueryParam("responsavel")
    private String responsavelId;
    @QueryParam("referenciaCobranca")
    private String referenciaCobranca;
    @QueryParam("contaId")
    private String contaId;

    @QueryParam("dataIni")
    private String dataIni;
    @QueryParam("dataFim")
    private String dataFim;
}
