package org.acme.auth.api.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.auth.api.dto.UsersDTO;
import org.acme.auth.api.request.UsersRequest;
import org.acme.auth.domain.model.Users;
import org.acme.auth.domain.service.UsersService;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersController {

    private UsersService usersService;
    private ModelMapper modelMapper;

    public UsersController(UsersService usersService, ModelMapper modelMapper){
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    @POST
    @Transactional
    public Response inserir(UsersRequest request){
        Users user = usersService.inserirUser(request);
        return Response.status(Response.Status.CREATED).entity(modelMapper.map(user, UsersDTO.class)).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletar(@PathParam("id") UUID id){
        usersService.deletarUser(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") UUID id){
        Users user = usersService.buscarUserPorId(id);
        return Response.status(Response.Status.OK).entity(modelMapper.map(user, UsersDTO.class)).build();
    }

    @GET
    @Path("/all")
    public Response buscarTodos(){
        List<Users> lista = usersService.listarUser();
        List<UsersDTO> dto = new ArrayList<>();
        for(Users user : lista) {
            dto.add(modelMapper.map(user, UsersDTO.class));
        }
        return Response.status(Response.Status.OK).entity(dto).build();
    }
}
