package org.acme.api.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.DespesaDTO;
import org.acme.api.filter.DespesaFilter;
import org.acme.api.request.DespesaRequest;
import org.acme.domain.model.Despesa;
import org.acme.domain.service.DespesaService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/despesa")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DespesaController {

    private DespesaService despesaService;

    public DespesaController(DespesaService despesaService){
        this.despesaService = despesaService;
    }

    @POST
    @Transactional
    public Response inserirDespesa(DespesaRequest despesaRequest){
        Despesa despesa = despesaService.inserirDespesa(despesaRequest);
        return Response.status(Response.Status.CREATED).entity(DespesaDTO.entityFromDTO(despesa)).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizarDespesa(@PathParam("id") UUID id, DespesaRequest despesaRequest){
        Despesa despesa = despesaService.atualizarDespesa(despesaRequest, id);
        return Response.status(Response.Status.OK).entity(DespesaDTO.entityFromDTO(despesa)).build();
    }

    @GET
    @Path("{id}")
    @Transactional
    public Response buscarPorId(@PathParam("id") String id){
        Despesa despesa = despesaService.buscarDespesaPorId(UUID.fromString(id));
        return Response.status(Response.Status.OK).entity(DespesaDTO.entityFromDTO(despesa)).build();
    }

    @GET
    @Path("filtros")
    @Transactional
    public Response buscarComFiltros(@BeanParam DespesaFilter despesaFilter,
                                     @QueryParam("page") int page,
                                     @QueryParam("size") int size){
        List<Despesa> despesas = despesaService.listar(despesaFilter, page, size);
        List<DespesaDTO> despesasDTO = despesas.stream().map(DespesaDTO::entityFromDTO).collect(Collectors.toList());
        return Response.status(Response.Status.OK).entity(despesasDTO).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletarDespesa(@PathParam("id") UUID id){
        despesaService.deletarDespesa(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
