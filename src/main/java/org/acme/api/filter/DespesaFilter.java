package org.acme.api.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Data;

@Data
public class DespesaFilter {
    @QueryParam("fornecedor")
    private String fornecedorId;
    @QueryParam("banco")
    private Integer bancoId;
    @QueryParam("grupo")
    private Integer grupoId;
    @QueryParam("situacao")
    private Integer situacao;
}
