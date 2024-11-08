package org.acme.api.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.DespesaDTO;
import org.acme.api.request.DespesaRequest;
import org.acme.domain.model.Despesa;
import org.acme.domain.service.DespesaService;

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
}
