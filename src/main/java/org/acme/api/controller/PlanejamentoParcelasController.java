package org.acme.api.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.api.dto.ParcelaDTO;
import org.acme.api.dto.PlanejamentoParcelasDTO;
import org.acme.domain.model.Parcela;
import org.acme.domain.service.PlanejamentoParcelasService;
import org.modelmapper.ModelMapper;

import java.util.List;

@Path("/planejamento-parcela")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlanejamentoParcelasController {

    private PlanejamentoParcelasService planejamentoParcelasService;
    private ModelMapper modelMapper;

    @Inject
    public PlanejamentoParcelasController(PlanejamentoParcelasService planejamentoParcelasService, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.planejamentoParcelasService = planejamentoParcelasService;
    }

    @POST
    @Path("/calcular")
    @Transactional
    public Response calcularPlanejamentoParcelas(List<ParcelaDTO> parcelas) {
        List<PlanejamentoParcelasDTO> planejamento = planejamentoParcelasService.criarPlanejamentoParcelas(parcelas);
        return Response.status(Response.Status.CREATED).entity(planejamento).build();
    }
}
