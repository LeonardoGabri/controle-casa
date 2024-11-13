package org.acme.api.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Data;

@Data
public class SubgrupoFilter {
    @QueryParam("nome")
    private String nome;

    @QueryParam("grupo")
    private String grupoId;


}
