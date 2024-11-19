package org.acme.api.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.ParcelaDTO;
import org.acme.api.filter.ParcelaFilter;
import org.acme.api.request.ParcelaRequest;
import org.acme.domain.model.Parcela;
import org.acme.domain.service.ParcelaService;

import java.util.List;
import java.util.UUID;

@Path("/parcela")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ParcelaController {

private ParcelaService parcelaService;
    public ParcelaController(ParcelaService parcelaService){
        this.parcelaService = parcelaService;
    }

    @GET
    @Path("/filtros")
    @Transactional
    public Response buscarComFiltros(@BeanParam ParcelaFilter parcelaFilter,
                                   @QueryParam("page") int page,
                                   @QueryParam("size") int size){
        List<Parcela> parcelas = parcelaService.listar(parcelaFilter, page, size);
        return Response.status(Response.Status.CREATED).entity(ParcelaDTO.fromEntityList(parcelas)).build();
    }

    @GET
    @Path("{id}")
    @Transactional
    public Response buscarPorId(@PathParam("id") String id){
        Parcela parcela = parcelaService.buscarParcelaPorId(UUID.fromString(id));
        return Response.status(Response.Status.CREATED).entity(ParcelaDTO.entityFromDTO(parcela)).build();
    }

    @POST
    @Transactional
    public Response inserirParcela(ParcelaRequest parcelaRequest){
        Parcela parcela = parcelaService.inserirParcela(parcelaRequest);
        return Response.status(Response.Status.CREATED).entity(ParcelaDTO.entityFromDTO(parcela)).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizarParcelas(@PathParam("id") String id, ParcelaRequest parcelaRequest){
        Parcela parcela = parcelaService.atualizarParcela(parcelaRequest, UUID.fromString(id));
        return Response.status(Response.Status.OK).entity(ParcelaDTO.entityFromDTO(parcela)).build();
    }

    @PUT
    @Path("pagar-parcela/{id}")
    @Transactional
    public Response pagarParcela(@PathParam("id") String id){
        Parcela parcela = parcelaService.pagarParcela(UUID.fromString(id));
        return Response.status(Response.Status.CREATED).entity(ParcelaDTO.entityFromDTO(parcela)).build();
    }
}
