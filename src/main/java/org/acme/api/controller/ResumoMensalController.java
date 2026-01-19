package org.acme.api.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.filter.ResumoMensalFilter;
import org.acme.domain.service.ResumoMensalService;

@Path("/resumo-mensal")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResumoMensalController {

    private ResumoMensalService service;
    public ResumoMensalController(ResumoMensalService service){
        this.service = service;
    }

    @GET
    @Path("/buscar/acerto-responsaveis")
    public Response buscarResumoMensal(@BeanParam ResumoMensalFilter referenciaCobranca,
                                       @QueryParam("size") int size) {
        return Response.status(Response.Status.OK).entity(service.buscarAcertoResponsavel(referenciaCobranca.getReferenciaCobranca())).build();
    }

    @GET
    @Path("/buscar/obrigacoes-financeiras")
    public Response buscarObrigacoesFinanceiras(
            @BeanParam ResumoMensalFilter referenciaCobranca
    ) {
        return Response.ok(
                service.buscarObrigacaoFinanceira(
                        referenciaCobranca.getReferenciaCobranca()
                )
        ).build();
    }
}
