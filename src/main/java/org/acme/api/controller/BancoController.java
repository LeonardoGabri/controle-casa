package org.acme.api.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.BancoDTO;
import org.acme.domain.service.BancoService;

import java.util.List;

@Path("/banco")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BancoController {

    private BancoService bancoService;

    public BancoController(BancoService bancoService){
        this.bancoService = bancoService;
    }

    @GET
    public Response listarTodos(){
        List<BancoDTO> lista = bancoService.listar();
        return Response.status(Response.Status.OK).entity(lista).build();
    }
}
