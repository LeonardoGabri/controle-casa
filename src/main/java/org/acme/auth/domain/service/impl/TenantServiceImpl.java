package org.acme.auth.domain.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.auth.api.request.TenantRequest;
import org.acme.auth.domain.model.Tenant;
import org.acme.auth.domain.repository.TenantRepository;
import org.acme.auth.domain.service.TenantService;
import org.acme.domain.model.Responsavel;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TenantServiceImpl implements TenantService {
    private final String MSG_NAO_ENCONTRADO = "Não encontrado registro com id = %s";
    private final String ERRO_AO_DELETAR = "erro ao deletar registro";

    ModelMapper modelMapper;
    private TenantRepository tenantRepository;

    public TenantServiceImpl(ModelMapper modelMapper, TenantRepository tenantRepository){
        this.modelMapper = modelMapper;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Tenant inserirTenant(TenantRequest request) {
        Tenant tenant = modelMapper.map(request, Tenant.class);
        tenantRepository.persist(tenant);
        return tenant;
    }

    @Override
    public List<Tenant> listarTenant() {
        return tenantRepository.findAll().stream().toList();
    }

    @Override
    public Tenant buscarTenantPorId(UUID id) {
        Tenant tenant = tenantRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(MSG_NAO_ENCONTRADO, id)));
        return tenant;
    }

    @Override
    public void deletarTenant(UUID id) {
        try{
            Tenant tenant = buscarTenantPorId(id);
            tenantRepository.delete(tenant);
        }catch (RuntimeException e){
            throw new RuntimeException(String.format(ERRO_AO_DELETAR));
        }
    }
}
