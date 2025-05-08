package org.acme.auth.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.auth.domain.model.Users;

import java.util.Optional;
import java.util.UUID;
@ApplicationScoped
public class UsersRepository implements PanacheRepository<Users> {
    public Optional<Users> findById(UUID id) {
        return find("id", id).firstResultOptional();
    }
    public Optional<Users> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }
}
