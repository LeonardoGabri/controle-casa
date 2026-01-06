package org.acme.api.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.ParcelaDTO;
import org.acme.api.dto.ParcelaValorTotalDTO;
import org.acme.api.filter.ParcelaFilter;
import org.acme.api.request.DespesaRequest;
import org.acme.api.request.ParcelaRequest;
import org.acme.domain.model.Parcela;
import org.acme.domain.service.ParcelaService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/parcela")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ParcelaController {

    private ParcelaService parcelaService;
    private ModelMapper modelMapper;

    public ParcelaController(ParcelaService parcelaService, ModelMapper modelMapper) {
        this.parcelaService = parcelaService;
        this.modelMapper = modelMapper;
    }

    @GET
    @Path("/filtros")
    @Transactional
    public Response buscarComFiltros(@BeanParam ParcelaFilter parcelaFilter,
                                     @QueryParam("page") int page,
                                     @QueryParam("size") int size) {
        List<Parcela> parcelas = parcelaService.listar(parcelaFilter, page, size);
        return Response.status(Response.Status.CREATED).entity(ParcelaValorTotalDTO.responseDto(parcelas.stream().map(item -> modelMapper.map(item, ParcelaDTO.class)).collect(Collectors.toList()))).build();
    }

    @GET
    @Path("{id}")
    @Transactional
    public Response buscarPorId(@PathParam("id") String id) {
        Parcela parcela = parcelaService.buscarParcelaPorId(UUID.fromString(id));
        return Response.status(Response.Status.CREATED).entity(modelMapper.map(parcela, ParcelaDTO.class)).build();
    }

    @POST
    @Transactional
    public Response inserirParcela(ParcelaRequest parcelaRequest) {
        Parcela parcela = parcelaService.inserirParcela(parcelaRequest);
        return Response.status(Response.Status.CREATED).entity(modelMapper.map(parcela, ParcelaDTO.class)).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizarParcelas(@PathParam("id") String id, ParcelaRequest parcelaRequest) {
        Parcela parcela = parcelaService.atualizarParcela(parcelaRequest, UUID.fromString(id));
        return Response.status(Response.Status.OK).entity(modelMapper.map(parcela, ParcelaDTO.class)).build();
    }

    @POST
    @Path("calcular-parcelas")
    @Transactional
    public Response calcularParcelas(DespesaRequest despesaRequest) {
        List<ParcelaDTO> parcelas = parcelaService.calcularParcelas(despesaRequest);
        return Response.status(Response.Status.OK).entity(parcelas).build();
    }
}
