package org.acme.api.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.ResponsavelDTO;
import org.acme.api.dto.ValoresDTO;
import org.acme.api.filter.ResponsavelFilter;
import org.acme.api.request.ResponsavelRequest;
import org.acme.domain.model.Responsavel;
import org.acme.domain.service.ParcelaService;
import org.acme.domain.service.ResponsavelService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/responsavel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResponsavelController {

    private ResponsavelService responsavelService;
    private ParcelaService parcelaService;

    @Inject
    public ResponsavelController(ResponsavelService responsavelService, ParcelaService parcelaService){
        this.responsavelService = responsavelService;
        this.parcelaService = parcelaService;
    }

    @POST
    @Transactional
    public Response inserirResponsavel(ResponsavelRequest responsavelRequest){
        Responsavel responsavel = responsavelService.inserirResponsavel(responsavelRequest);
        return Response.status(Response.Status.CREATED).entity(ResponsavelDTO.entityFromDTO(responsavel)).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizarResponsavel(@PathParam("id") UUID id, ResponsavelRequest responsavelRequest){
        Responsavel responsavel = responsavelService.atualizarResponsavel(responsavelRequest, id);
        return Response.status(Response.Status.OK).entity(ResponsavelDTO.entityFromDTO(responsavel)).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletarResponsavel(@PathParam("id") UUID id){
        responsavelService.deletarResponsavel(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") UUID id){
        Responsavel responsavel = responsavelService.buscarResponsavelPorId(id);
        return Response.status(Response.Status.OK).entity(ResponsavelDTO.entityFromDTO(responsavel)).build();
    }

    @GET
    @Path("/filtros")
    public Response buscarComFiltros(@BeanParam ResponsavelFilter responsavelFilter,
                                     @QueryParam("page") int page,
                                     @QueryParam("size") int size){
        List<Responsavel> lista = responsavelService.listarResponsavelFiltros(responsavelFilter, page, size);
        List<ResponsavelDTO> dto = new ArrayList<>();
        for (Responsavel responsavel : lista) {
            ResponsavelDTO responsavelDTO = ResponsavelDTO.entityFromDTO(responsavel);
            dto.add(responsavelDTO);
        }
        return Response.status(Response.Status.OK).entity(dto).build();
    }

}
