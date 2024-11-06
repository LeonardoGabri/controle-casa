package org.acme.api.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Data;

@Data
public class GrupoFilter {
    @QueryParam("nome")
    private String nome;
}
