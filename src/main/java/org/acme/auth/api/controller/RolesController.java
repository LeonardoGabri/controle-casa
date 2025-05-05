package org.acme.auth.api.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.filter.ResponsavelFilter;
import org.acme.auth.api.request.RolesRequest;
import org.acme.auth.api.request.TenantRequest;
import org.acme.auth.domain.model.Roles;
import org.acme.auth.domain.model.Tenant;
import org.acme.auth.domain.service.RolesService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

@Path("/roles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RolesController {

    private RolesService rolesService;
    private ModelMapper modelMapper;

    public RolesController(RolesService rolesService, ModelMapper modelMapper){
        this.rolesService = rolesService;
        this.modelMapper = modelMapper;
    }

    @POST
    @Transactional
    public Response inserir(RolesRequest request){
        Roles roles = rolesService.inserirRoles(request);
        return Response.status(Response.Status.CREATED).entity(roles).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletar(@PathParam("id") UUID id){
        rolesService.deletarRoles(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") UUID id){
        Roles roles = rolesService.buscarRolesPorId(id);
        return Response.status(Response.Status.OK).entity(roles).build();
    }

    @GET
    @Path("/all")
    public Response buscarTodos(){
        List<Roles> lista = rolesService.listarRoles();

        return Response.status(Response.Status.OK).entity(lista).build();
    }
}
