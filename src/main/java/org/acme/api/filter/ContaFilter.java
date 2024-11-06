package org.acme.api.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Data;

@Data
public class ContaFilter {
    @QueryParam("banco")
    private String bancoId;

    @QueryParam("responsavel")
    private String responsavelId;
}
