package org.acme.auth.api.request;

import lombok.Data;

@Data
public class UsersRequest {
    private String username;
    private String password;
    private String tenantId;
}
