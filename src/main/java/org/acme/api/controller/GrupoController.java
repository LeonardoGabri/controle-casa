package org.acme.api.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.GrupoDTO;
import org.acme.api.filter.GrupoFilter;
import org.acme.api.request.GrupoRequest;
import org.acme.domain.model.Grupo;
import org.acme.domain.service.GrupoService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/grupo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GrupoController {

    private GrupoService grupoService;
    private ModelMapper modelMapper;

    @Inject
    public GrupoController(GrupoService grupoService, ModelMapper modelMapper){
        this.grupoService = grupoService;
        this.modelMapper = modelMapper;
    }

    @POST
    @Transactional
    public Response inserirResponsavel(GrupoRequest grupoRequest){
        Grupo grupo = grupoService.inserirGrupo(grupoRequest);
        return Response.status(Response.Status.CREATED).entity(modelMapper.map(grupo, Grupo.class)).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizarResponsavel(@PathParam("id") UUID id, GrupoRequest grupoRequest){
        Grupo grupo = grupoService.atualizarGrupo(grupoRequest, id);
        return Response.status(Response.Status.OK).entity(modelMapper.map(grupo, Grupo.class)).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletarResponsavel(@PathParam("id") UUID id){
        grupoService.deletarGrupo(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") UUID id){
        return Response.status(Response.Status.OK).entity(grupoService.buscarGrupoPorId(id)).build();
    }

    @GET
    @Path("/filtros")
    public Response buscarComFiltros(@BeanParam GrupoFilter grupoFilter,
                                     @QueryParam("page") int page,
                                     @QueryParam("size") int size){
        List<Grupo> lista = grupoService.listarGrupoFiltros(grupoFilter, page, size);
        return Response.status(Response.Status.OK).entity(lista.stream().map(item -> modelMapper.map(item, Grupo.class)).collect(Collectors.toList())).build();
    }
}
