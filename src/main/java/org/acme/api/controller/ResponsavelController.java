package org.acme.api.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.ResponsavelDTO;
import org.acme.api.request.ResponsavelRequest;
import org.acme.domain.service.ResponsavelService;

import java.util.UUID;

@Path("/responsavel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResponsavelController {

    private ResponsavelService responsavelService;

    @Inject
    public ResponsavelController(ResponsavelService responsavelService){
        this.responsavelService = responsavelService;
    }

    @POST
    @Transactional
    public Response inserirResponsavel(ResponsavelRequest responsavelRequest){
        ResponsavelDTO responsavelDTO = responsavelService.inserirResponsavel(responsavelRequest);
        return Response.status(Response.Status.CREATED).entity(responsavelDTO).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizarResponsavel(@PathParam("id") UUID id, ResponsavelRequest responsavelRequest){
        ResponsavelDTO responsavelDTO = responsavelService.atualizarResponsavel(responsavelRequest, id);
        return Response.status(Response.Status.OK).entity(responsavelDTO).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletarResponsavel(@PathParam("id") UUID id){
        responsavelService.deletarResponsavel(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
