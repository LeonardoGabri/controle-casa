package org.acme.api.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.PatrimonioDTO;
import org.acme.api.dto.TransacaoDTO;
import org.acme.api.filter.PatrimonioFilter;
import org.acme.api.filter.TransacaoFilter;
import org.acme.api.request.PatrimonioRequest;
import org.acme.api.request.TransacaoRequest;
import org.acme.domain.model.Patrimonio;
import org.acme.domain.model.Transacao;
import org.acme.domain.service.PatrimonioService;
import org.acme.domain.service.TransacaoService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/transacao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransacaoController {

    private TransacaoService transacaoService;
    private ModelMapper modelMapper;

    @Inject
    public TransacaoController(TransacaoService transacaoService, ModelMapper modelMapper) {
        this.transacaoService = transacaoService;
        this.modelMapper = modelMapper;
    }

    @POST
    @Transactional
    public Response inserirTransacao(TransacaoRequest transacaoRequest) {
        Transacao transacao = transacaoService.inserir(transacaoRequest);
        return Response.status(Response.Status.CREATED).entity(modelMapper.map(transacao, TransacaoDTO.class)).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizarTransacao(@PathParam("id") UUID id, TransacaoRequest transacaoRequest) {
        Transacao transacao = transacaoService.atualizar(transacaoRequest, id);
        return Response.status(Response.Status.OK).entity(modelMapper.map(transacao, TransacaoDTO.class)).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletarTransacao(@PathParam("id") UUID id) {
        transacaoService.deletar(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") UUID id) {
        return Response.status(Response.Status.OK).entity(modelMapper.map(transacaoService.buscarPorId(id), TransacaoDTO.class)).build();
    }

        @GET
        @Path("/filtros")
        public Response buscarComFiltros(@BeanParam TransacaoFilter filter,
                                         @QueryParam("page") int page,
                                         @QueryParam("size") int size) {
            List<Transacao> lista = transacaoService.listarComFiltros(filter, page, size);
            return Response.status(Response.Status.OK).entity(lista.stream().map(item -> modelMapper.map(item, TransacaoDTO.class)).collect(Collectors.toList())).build();
        }
}
