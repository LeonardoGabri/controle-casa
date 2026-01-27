package org.acme.api.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Data;

@Data
public class ResponsavelFilter {
    @QueryParam("nome")
    private String nome;

    @QueryParam("titular")
    private Boolean titular;
}
