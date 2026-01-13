package org.acme.api.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Data;
import org.acme.domain.enums.TipoPatrimonioEnum;

@Data
public class PatrimonioFilter {
    @QueryParam("conta")
    private String conta;

    @QueryParam("tipo")
    private TipoPatrimonioEnum tipo;
}
