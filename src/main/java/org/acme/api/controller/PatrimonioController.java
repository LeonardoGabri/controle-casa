package org.acme.api.controller;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.ContaDTO;
import org.acme.api.dto.PatrimonioDTO;
import org.acme.api.filter.ContaFilter;
import org.acme.api.filter.PatrimonioFilter;
import org.acme.api.request.ContaRequest;
import org.acme.api.request.PatrimonioRequest;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Patrimonio;
import org.acme.domain.service.ContaService;
import org.acme.domain.service.PatrimonioService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/patrimonio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PatrimonioController {

    private PatrimonioService patrimonioService;
    private ModelMapper modelMapper;

    @Inject
    public PatrimonioController(PatrimonioService patrimonioService, ModelMapper modelMapper) {
        this.patrimonioService = patrimonioService;
        this.modelMapper = modelMapper;
    }

    @POST
    @Transactional
    public Response inserirPatrimonio(PatrimonioRequest patrimonioRequest) {
        Patrimonio patrimonio = patrimonioService.inserir(patrimonioRequest);
        return Response.status(Response.Status.CREATED).entity(modelMapper.map(patrimonio, PatrimonioDTO.class)).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizarPatrimonio(@PathParam("id") UUID id, PatrimonioRequest patrimonioRequest) {
        Patrimonio patrimonio = patrimonioService.atualizar(patrimonioRequest, id);
        return Response.status(Response.Status.OK).entity(modelMapper.map(patrimonio, PatrimonioDTO.class)).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletarPatrimonio(@PathParam("id") UUID id) {
        patrimonioService.deletar(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") UUID id) {
        return Response.status(Response.Status.OK).entity(modelMapper.map(patrimonioService.buscarPorId(id), PatrimonioDTO.class)).build();
    }

        @GET
        @Path("/filtros")
        public Response buscarComFiltros(@BeanParam PatrimonioFilter patrimonioFilter,
                                         @QueryParam("page") int page,
                                         @QueryParam("size") int size) {
            List<Patrimonio> lista = patrimonioService.listarComFiltros(patrimonioFilter, page, size);
            return Response.status(Response.Status.OK).entity(lista.stream().map(item -> modelMapper.map(item, PatrimonioDTO.class)).collect(Collectors.toList())).build();
        }
}
