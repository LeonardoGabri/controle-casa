package org.acme.api.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.ContaDTO;
import org.acme.api.dto.FornecedorDTO;
import org.acme.api.filter.ContaFilter;
import org.acme.api.filter.FornecedorFilter;
import org.acme.api.request.ContaRequest;
import org.acme.api.request.FornecedorRequest;
import org.acme.domain.service.ContaService;
import org.acme.domain.service.FornecedorService;

import java.util.List;
import java.util.UUID;

@Path("/fornecedor")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FornecedorController {

    private FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @POST
    @Transactional
    public Response inserirFornecedor(FornecedorRequest fornecedorRequest){
        FornecedorDTO fornecedorDTO = fornecedorService.inserirFornecedor(fornecedorRequest);
        return Response.status(Response.Status.CREATED).entity(fornecedorDTO).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizarFornecedor(@PathParam("id") UUID id, FornecedorRequest fornecedorRequest){
        FornecedorDTO fornecedorDTO = fornecedorService.atualizarFornecedor(fornecedorRequest, id);
        return Response.status(Response.Status.OK).entity(fornecedorDTO).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletarFornecedor(@PathParam("id") UUID id){
        fornecedorService.deletarFornecedor(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") UUID id){
        return Response.status(Response.Status.OK).entity(fornecedorService.buscarFornecedorPorId(id)).build();
    }

    @GET
    @Path("/filtros")
    public Response buscarComFiltros(@BeanParam FornecedorFilter fornecedorFilter,
                                     @QueryParam("page") int page,
                                     @QueryParam("size") int size){
        List<FornecedorDTO> lista = fornecedorService.listarFornecedorFiltros(fornecedorFilter, page, size);
        return Response.status(Response.Status.OK).entity(lista).build();
    }
}
