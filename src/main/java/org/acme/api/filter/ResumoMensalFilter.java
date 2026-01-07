package org.acme.api.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Data;
import org.acme.infra.validacao.ReferenciaCobranca;

@Data
public class ResumoMensalFilter {
    @QueryParam("referenciaCobranca")
    @ReferenciaCobranca
    private String referenciaCobranca;
}
