package org.acme.api.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.PatrimonioDTO;
import org.acme.api.filter.PatrimonioFilter;
import org.acme.api.request.CriptomoedaRequest;
import org.acme.api.request.PatrimonioRequest;
import org.acme.domain.model.Criptomoeda;
import org.acme.domain.model.Patrimonio;
import org.acme.domain.service.CriptomoedaService;
import org.acme.domain.service.PatrimonioService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/criptomoeda")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CriptomoedaController {

    private CriptomoedaService criptomoedaService;
    private ModelMapper modelMapper;

    @Inject
    public CriptomoedaController(CriptomoedaService criptomoedaService, ModelMapper modelMapper) {
        this.criptomoedaService = criptomoedaService;
        this.modelMapper = modelMapper;
    }

    @POST
    @Transactional
    public Response inserirCriptomoeda(CriptomoedaRequest request) {
        Criptomoeda criptomoeda = criptomoedaService.inserir(request);
        return Response.status(Response.Status.CREATED).entity(criptomoeda).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizarPatrimonio(@PathParam("id") UUID id, CriptomoedaRequest request) {
        Criptomoeda criptomoeda = criptomoedaService.atualizar(request, id);
        return Response.status(Response.Status.CREATED).entity(criptomoeda).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletarPatrimonio(@PathParam("id") UUID id) {
        criptomoedaService.deletar(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") UUID id) {
        return Response.status(Response.Status.OK).entity(criptomoedaService.buscarPorId(id)).build();
    }

    @GET
    @Path("codigo/{codigo}")
    public Response buscarPorCodigo(@PathParam("codigo") String codigo) {
        return Response.status(Response.Status.OK).entity(criptomoedaService.buscarPorCodigo(codigo)).build();
    }

    @GET
    @Path("/listar-todos")
    public Response buscarComFiltros() {
        List<Criptomoeda> lista = criptomoedaService.listar();
        return Response.status(Response.Status.OK).entity(lista).build();
    }
}
