package org.acme.auth;

import io.smallrye.jwt.build.Jwt;

import java.time.Instant;
import java.util.Set;

public class JwtGenerator {
    public static String generateJwt(String username, Set<String> roles) {
        return Jwt.issuer("example.com")
                .upn(username)
                .groups(roles)
                .expiresAt(Instant.now().plusSeconds(1200))
                .sign();
    }
}
