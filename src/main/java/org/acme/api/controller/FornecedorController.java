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
import org.acme.domain.model.Fornecedor;
import org.acme.domain.service.ContaService;
import org.acme.domain.service.FornecedorService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/fornecedor")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FornecedorController {

    private FornecedorService fornecedorService;
    private ModelMapper modelMapper;

    public FornecedorController(FornecedorService fornecedorService, ModelMapper modelMapper) {
        this.fornecedorService = fornecedorService;
        this.modelMapper = modelMapper;
    }

    @POST
    @Transactional
    public Response inserirFornecedor(FornecedorRequest fornecedorRequest){
        Fornecedor fornecedor = fornecedorService.inserirFornecedor(fornecedorRequest);
        return Response.status(Response.Status.CREATED).entity(modelMapper.map(fornecedor, FornecedorDTO.class)).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizarFornecedor(@PathParam("id") UUID id, FornecedorRequest fornecedorRequest){
        Fornecedor fornecedor = fornecedorService.atualizarFornecedor(fornecedorRequest, id);
        return Response.status(Response.Status.OK).entity(modelMapper.map(fornecedor, FornecedorDTO.class)).build();
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
        Fornecedor fornecedor = fornecedorService.buscarFornecedorPorId(id);
        return Response.status(Response.Status.OK).entity(modelMapper.map(fornecedor, FornecedorDTO.class)).build();
    }

    @GET
    @Path("/filtros")
    public Response buscarComFiltros(@BeanParam FornecedorFilter fornecedorFilter,
                                     @QueryParam("page") int page,
                                     @QueryParam("size") int size){
        List<Fornecedor> lista = fornecedorService.listarFornecedorFiltros(fornecedorFilter, page, size);
        return Response.status(Response.Status.OK).entity(lista.stream().map(item -> modelMapper.map(item, FornecedorDTO.class)).collect(Collectors.toList())).build();
    }
}
