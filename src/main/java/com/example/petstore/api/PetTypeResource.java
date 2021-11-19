package com.example.petstore.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.petstore.model.PetType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import static com.example.petstore.data.DataStore.petTypes;

@Path("/petTypes")
@Produces("application/json")
public class PetTypeResource {

//    private List<PetType> petTypes = new ArrayList<PetType>();

    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Initialize Pet Types",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))) })
    @GET
    public Response initializePetTypes() {

        PetType petType1 = new PetType(1, "Dog");
        PetType petType2 = new PetType(2, "Cat");
        PetType petType3 = new PetType(3, "Parrot");

        petTypes.add(petType1);
        petTypes.add(petType2);
        petTypes.add(petType3);

        return Response.ok(petTypes).build();
    }


    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "All Pet Types",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))) })
    @GET
    @Path("/getPetTypes")
    public Response getPetTypes() {

        return Response.ok(petTypes).build();
    }


    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "PetType for id",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))),
            @APIResponse(responseCode = "404", description = "No PetTypes found for the id.") })
    @GET
    @Path("/getPetType/{petTypeId}")
    public Response getPetType(@PathParam("petTypeId") int petTypeId) {
        if (petTypeId < 0) {
            return Response.status(Status.NOT_FOUND).build();
        }

        PetType petType = new PetType();
        for (int i = 0;i<petTypes.size();i++){
            if(petTypeId == petTypes.get(i).getId()){
                petType.setId(petTypeId);
                petType.setType(petTypes.get(i).getType());
            }
        }

        return Response.ok(petType).build();

    }


    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Delete Pet Type for id",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))),
            @APIResponse(responseCode = "404", description = "No Pet Type found for the id.") })
    @GET
    @Path("/deletePetType/{petTypeId}")
    public Response deletePetType(@PathParam("petTypeId") int petTypeId) {
        if (petTypeId < 0) {
            return Response.status(Status.NOT_FOUND).build();
        }

        for (int i = 0;i<petTypes.size();i++){
            if(petTypeId == petTypes.get(i).getId()){
                petTypes.remove(i);
            }
        }

        return Response.ok(petTypes).build();

    }


    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Add Pet Type",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))) })
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/addPetType")
    public Response addPetType(@FormParam("id") String id, @FormParam("type") String type) {

        if (Integer.parseInt(id) < 0) {
            return Response.status(Status.NOT_FOUND).build();
        }

        for (int i = 0;i<petTypes.size();i++){
            if(Integer.parseInt(id) == petTypes.get(i).getId()){
                return Response.status(Status.NOT_ACCEPTABLE).build();
            }
        }

        PetType petType = new PetType();
        petType.setId(Integer.parseInt(id));
        petType.setType(type);

        petTypes.add(petType);

        return Response.ok(petTypes).build();

    }


    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Add Pet",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))) })
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/updatePetType")
    public Response updatePetType(@FormParam("id") String id, @FormParam("type") String type) {
        if (Integer.parseInt(id) < 0) {
            return Response.status(Status.NOT_FOUND).build();
        }

        for (int i = 0;i<petTypes.size();i++){
            if(Integer.parseInt(id) == petTypes.get(i).getId()){
                petTypes.get(i).setType(type);
                break;
            }
        }


        return Response.ok(petTypes).build();

    }

}
