package org.acme.api.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Data;

@Data
public class ParcelaFilter {
    @QueryParam("responsavel")
    private String responsavelId;
    @QueryParam("mesReferencia")
    private Integer mesReferencia;
    @QueryParam("anoReferencia")
    private Integer anoReferencia;
    @QueryParam("situacao")
    private String situacao;
}
