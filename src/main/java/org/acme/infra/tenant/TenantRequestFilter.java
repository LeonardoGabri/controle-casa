package org.acme.infra.tenant;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.acme.auth.domain.model.Users;
import org.acme.auth.domain.service.UsersService;
import org.acme.infra.context.TenantContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.io.IOException;
import java.util.Optional;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class TenantRequestFilter implements ContainerRequestFilter {
    @Inject
    JsonWebToken jwt;

    @Inject
    UsersService usersService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        String path = containerRequestContext.getUriInfo().getPath();

        if (path.startsWith("auth/login")) {
            return;
        }

        String username = jwt.getClaim("upn");

        if (username == null) {
            return;
        }

        Optional<Users> usuario = usersService.findByUsername(username);

        if (usuario.isPresent()) {
            TenantContext.setTenant(usuario.get().getTenant().getId());
            TenantContext.setUsername(usuario.get().getUsername());
        }

    }
}
