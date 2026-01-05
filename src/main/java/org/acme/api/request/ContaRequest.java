package org.acme.api.request;

import lombok.Data;
import org.acme.domain.enums.TipoContaEnum;

@Data
public class ContaRequest {
    private String bancoId;
    private String responsavelId;
    private TipoContaEnum tipo;

}
