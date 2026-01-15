package org.acme.auth.domain.service;

import org.acme.auth.api.request.UsersRequest;
import org.acme.auth.domain.model.Tenant;
import org.acme.auth.domain.model.Users;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersService {
    public Users inserirUser(UsersRequest request);
    public List<Users> listarUser();
    public Users buscarUserPorId(UUID id);

    public void deletarUser(UUID id);

    Optional<Users> findByUsername(String username);


}
