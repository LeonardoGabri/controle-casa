package org.acme.api.request;

import lombok.Data;

@Data
public class ContaRequest {
    private String bancoId;
    private String responsavelId;
}
