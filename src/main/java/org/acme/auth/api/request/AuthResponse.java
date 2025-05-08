package org.acme.auth.api.request;

public class AuthResponse {
    public String token;
    public AuthResponse(String token) {
        this.token = token;
    }
}
