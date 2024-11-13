package org.acme.api.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.SubgrupoDTO;
import org.acme.api.filter.SubgrupoFilter;
import org.acme.api.request.SubgrupoRequest;
import org.acme.domain.model.Subgrupo;
import org.acme.domain.service.SubgrupoService;

import java.util.List;
import java.util.UUID;

@Path("/subgrupo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubgrupoController {

    private SubgrupoService subgrupoService;

    @Inject
    public SubgrupoController(SubgrupoService subgrupoService){
        this.subgrupoService = subgrupoService;
    }

    @POST
    @Transactional
    public Response inserirResponsavel(SubgrupoRequest subgrupoRequest){
        Subgrupo subgrupo = subgrupoService.inserirSubgrupo(subgrupoRequest);
        return Response.status(Response.Status.CREATED).entity(SubgrupoDTO.entityFromDTO(subgrupo)).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizarResponsavel(@PathParam("id") UUID id, SubgrupoRequest subgrupoRequest){
        Subgrupo subgrupo = subgrupoService.atualizarSubgrupo(subgrupoRequest, id);
        return Response.status(Response.Status.OK).entity(SubgrupoDTO.entityFromDTO(subgrupo)).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletarResponsavel(@PathParam("id") UUID id){
        subgrupoService.deletarSubgrupo(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") UUID id){
        return Response.status(Response.Status.OK).entity(subgrupoService.buscarSubgrupoPorId(id)).build();
    }

    @GET
    @Path("/filtros")
    public Response buscarComFiltros(@BeanParam SubgrupoFilter subgrupoFilter,
                                     @QueryParam("page") int page,
                                     @QueryParam("size") int size){
        List<SubgrupoDTO> lista = subgrupoService.listarSubgrupoFiltros(subgrupoFilter, page, size);
        return Response.status(Response.Status.OK).entity(lista).build();
    }
}
