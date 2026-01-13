package org.acme.api.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Data;
import org.acme.domain.enums.TipoTransacaoEnum;

@Data
public class TransacaoFilter {
    @QueryParam("patrimonio")
    private String patrimonio;

    @QueryParam("mesTransacao")
    private String mesTransacao;

    @QueryParam("dataIni")
    private String dataIni;
    @QueryParam("dataFim")
    private String dataFim;

    @QueryParam("tipo")
    private TipoTransacaoEnum tipo;
}
