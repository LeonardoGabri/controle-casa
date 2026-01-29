package org.acme.auth.api.controller;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.auth.JwtGenerator;
import org.acme.auth.api.request.AuthRequest;
import org.acme.auth.api.request.AuthResponse;
import org.acme.auth.domain.model.Users;
import org.acme.auth.domain.service.UsersService;

import java.util.Set;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    UsersService usersService;

    public AuthController(UsersService usersService){
        this.usersService = usersService;
    }

    @POST
    @Path("/login")
    public Response login(AuthRequest request) {
        Users user = usersService.findByUsername(request.username)
                .orElseThrow(() -> new WebApplicationException("Usuário inválido", 401));

        if (!BcryptUtil.matches(request.password, user.getPassword())) {
            throw new WebApplicationException("Senha inválida", 401);
        }

        Set<String> roles = Set.of("user");
        String token = JwtGenerator.generateJwt(user.getUsername(), roles);
        return Response.ok(new AuthResponse(token)).build();
    }

    @GET
    @Path("/ping")
    @PermitAll
    public String ping() {
        return "ok";
    }
}
