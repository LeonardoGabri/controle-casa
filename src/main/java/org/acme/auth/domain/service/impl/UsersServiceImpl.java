package org.acme.auth.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.auth.api.request.UsersRequest;
import org.acme.auth.domain.model.Tenant;
import org.acme.auth.domain.model.Users;
import org.acme.auth.domain.repository.UsersRepository;
import org.acme.auth.domain.service.TenantService;
import org.acme.auth.domain.service.UsersService;
import org.modelmapper.ModelMapper;
import io.quarkus.elytron.security.common.BcryptUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@ApplicationScoped
public class UsersServiceImpl implements UsersService {
    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro por parametro = %s";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";

    private ModelMapper modelMapper;
    private UsersRepository usersRepository;
    private TenantService tenantService;

    public UsersServiceImpl(ModelMapper modelMapper, UsersRepository usersRepository, TenantService tenantService){
        this.modelMapper = modelMapper;
        this.usersRepository = usersRepository;
        this.tenantService = tenantService;
    }
    @Override
    public Users inserirUser(UsersRequest request) {
        String senhaCriptografada = BcryptUtil.bcryptHash(request.getPassword());
        Tenant tenant = tenantService.buscarTenantPorId(UUID.fromString(request.getTenantId()));
        Users user = Users.builder()
                .username(request.getUsername())
                .tenant(tenant)
                .password(senhaCriptografada)
                .build();
        usersRepository.persist(user);
        return user;
    }

    @Override
    public List<Users> listarUser() {
        return usersRepository.findAll().stream().toList();
    }

    @Override
    public Users buscarUserPorId(UUID id) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));
        return users;
    }

    @Override
    public void deletarUser(UUID id) {
        try{
            Users user = buscarUserPorId(id);
            usersRepository.delete(user);
        }catch (RuntimeException e){
            throw new RuntimeException(String.format(ERRO_AO_DELETAR));
        }
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return Optional.ofNullable(usersRepository.findByUsername(username).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, username))));
    }
}
