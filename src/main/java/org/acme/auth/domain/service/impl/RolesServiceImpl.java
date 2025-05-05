package org.acme.auth.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.auth.api.request.RolesRequest;
import org.acme.auth.domain.model.Roles;
import org.acme.auth.domain.model.Tenant;
import org.acme.auth.domain.repository.RolesRepository;
import org.acme.auth.domain.service.RolesService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RolesServiceImpl implements RolesService {
    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";

    private ModelMapper modelMapper;
    private RolesRepository rolesRepository;

    public RolesServiceImpl(ModelMapper modelMapper, RolesRepository rolesRepository){
        this.modelMapper = modelMapper;
        this.rolesRepository = rolesRepository;
    }
    @Override
    public Roles inserirRoles(RolesRequest request) {
        Roles role = modelMapper.map(request, Roles.class);
        rolesRepository.persist(role);
        return role;
    }

    @Override
    public List<Roles> listarRoles() {
        return rolesRepository.findAll().stream().toList();
    }

    @Override
    public Roles buscarRolesPorId(UUID id) {
        Roles roles = rolesRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));
        return roles;
    }

    @Override
    public void deletarRoles(UUID id) {
        try{
            Roles role = buscarRolesPorId(id);
            rolesRepository.delete(role);
        }catch (RuntimeException e){
            throw new RuntimeException(String.format(ERRO_AO_DELETAR));
        }
    }
}
