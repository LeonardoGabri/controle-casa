package org.acme.auth.api.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.auth.api.request.TenantRequest;
import org.acme.auth.domain.model.Tenant;
import org.acme.auth.domain.service.TenantService;

import java.util.List;
import java.util.UUID;

@Path("/tenant")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TenantController {

    private TenantService tenantService;

    public TenantController(TenantService tenantService){
        this.tenantService = tenantService;
    }

    @POST
    @Transactional
    public Response inserir(TenantRequest request){
        Tenant tenant = tenantService.inserirTenant(request);
        return Response.status(Response.Status.CREATED).entity(tenant).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletar(@PathParam("id") UUID id){
        tenantService.deletarTenant(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") UUID id){
        Tenant tenant = tenantService.buscarTenantPorId(id);
        return Response.status(Response.Status.OK).entity(tenant).build();
    }

    @GET
    @Path("/all")
    public Response buscarTodos(){
        List<Tenant> lista = tenantService.listarTenant();

        return Response.status(Response.Status.OK).entity(lista).build();
    }
}
