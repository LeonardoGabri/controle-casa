package org.acme.auth;

import io.smallrye.jwt.build.Jwt;

import java.util.Set;

public class JwtGenerator {
    public static String generateJwt(String username, Set<String> roles) {
        return Jwt.issuer("example.com")
                .upn(username)
                .groups(roles)
                .sign();
    }
}
