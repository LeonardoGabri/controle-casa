package org.acme.api.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.DespesaDTO;
import org.acme.api.dto.ParcelaDTO;
import org.acme.api.dto.PlanejamentoParcelasDTO;
import org.acme.api.dto.ResumoDespesaPorContaDTO;
import org.acme.api.filter.DespesaFilter;
import org.acme.api.request.DespesaRequest;
import org.acme.domain.model.Despesa;
import org.acme.domain.service.DespesaService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/despesa")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DespesaController {

    private DespesaService despesaService;
    private ModelMapper modelMapper;

    public DespesaController(DespesaService despesaService, ModelMapper modelMapper) {
        this.despesaService = despesaService;
        this.modelMapper = modelMapper;
    }

    @POST
    @Transactional
    public Response inserirDespesa(DespesaRequest despesaRequest) {
        Despesa despesa = despesaService.inserirDespesa(despesaRequest);
        return Response.status(Response.Status.CREATED).entity(modelMapper.map(despesa, DespesaDTO.class)).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizarDespesa(@PathParam("id") UUID id, DespesaRequest despesaRequest) {
        Despesa despesa = despesaService.atualizarDespesa(despesaRequest, id);
        return Response.status(Response.Status.OK).entity(modelMapper.map(despesa, DespesaDTO.class)).build();
    }

    @GET
    @Path("{id}")
    @Transactional
    public Response buscarPorId(@PathParam("id") String id) {
        Despesa despesa = despesaService.buscarDespesaPorId(UUID.fromString(id));
        DespesaDTO dto = modelMapper.map(despesa, DespesaDTO.class);
        dto.setParcelas(
                despesa.getParcelas().stream()
                        .map(p -> modelMapper.map(p, ParcelaDTO.class))
                        .toList()
        );
        dto.setPlanejamentoParcelas(
                despesa.getPlanejamentoParcelas().stream()
                        .map(p -> modelMapper.map(p, PlanejamentoParcelasDTO.class))
                        .toList()
        );
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    @GET
    @Path("filtros")
    @Transactional
    public Response buscarComFiltros(@BeanParam DespesaFilter despesaFilter,
                                     @QueryParam("page") int page,
                                     @QueryParam("size") int size) {
        List<Despesa> despesas = despesaService.listar(despesaFilter, page, size);
        List<DespesaDTO> despesasDTO = despesas.stream().map(item -> modelMapper.map(item, DespesaDTO.class)).collect(Collectors.toList());
        return Response.status(Response.Status.OK).entity(despesasDTO).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletarDespesa(@PathParam("id") UUID id) {
        despesaService.deletarDespesa(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("resumo-conta")
    @Transactional
    public Response resumoTotaisPorConta(@BeanParam DespesaFilter despesaFilter,
                                     @QueryParam("page") int page,
                                     @QueryParam("size") int size) {
      List<ResumoDespesaPorContaDTO> lista = despesaService.buscarResumoPorConta();
        return Response.status(Response.Status.OK).entity(lista).build();
    }
}
