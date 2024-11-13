package org.acme.api.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Data;
import org.acme.domain.enums.SituacaoEnum;

@Data
public class DespesaFilter {
    @QueryParam("fornecedor")
    private String fornecedorId;
    @QueryParam("conta")
    private String contaId;
    @QueryParam("subgrupo")
    private String subgrupoId;
    @QueryParam("situacao")
    private SituacaoEnum situacao;
}
