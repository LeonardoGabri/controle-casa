package org.acme.api.request;

import lombok.Data;

@Data
public class ResponsavelRequest {
    private String nome;
    private Boolean titular;
}
