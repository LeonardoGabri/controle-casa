package org.acme.api.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.ContaDTO;
import org.acme.api.filter.ContaFilter;
import org.acme.api.request.ContaRequest;
import org.acme.domain.model.Conta;
import org.acme.domain.service.ContaService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/conta")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContaController {

    private ContaService contaService;
    private ModelMapper modelMapper;

    @Inject
    public ContaController(ContaService contaService, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.contaService = contaService;
    }

    @POST
    @Transactional
    public Response inserirConta(ContaRequest contaRequest) {
        Conta conta = contaService.inserirConta(contaRequest);
        return Response.status(Response.Status.CREATED).entity(modelMapper.map(conta, ContaDTO.class)).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizarConta(@PathParam("id") UUID id, ContaRequest contaRequest) {
        Conta conta = contaService.atualizarConta(contaRequest, id);
        return Response.status(Response.Status.OK).entity(modelMapper.map(conta, ContaDTO.class)).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletarConta(@PathParam("id") UUID id) {
        contaService.deletarConta(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") UUID id) {
        return Response.status(Response.Status.OK).entity(contaService.buscarContaPorId(id)).build();
    }

    @GET
    @Path("/filtros")
    public Response buscarComFiltros(@BeanParam ContaFilter contaFilter,
                                     @QueryParam("page") int page,
                                     @QueryParam("size") int size) {
        List<Conta> lista = contaService.listarContaFiltros(contaFilter, page, size);
        return Response.status(Response.Status.OK).entity(lista.stream().map(item -> modelMapper.map(item, ContaDTO.class)).collect(Collectors.toList())).build();
    }
}
