package com.example.petstore.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.petstore.model.Pet;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import static com.example.petstore.data.DataStore.pets;

@Path("/pets")
@Produces("application/json")
public class PetResource {

//	private List<Pet> pets = new ArrayList<Pet>();

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Initialize Pets",
					content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	public Response initializePets() {

		Pet pet1 = new Pet(1, 3, "Lolo", "Dog");
		Pet pet2 = new Pet(2, 4, "Princess", "Cat");
		Pet pet3 = new Pet(3, 2, "Apa", "Parrot");
		Pet pet4 = new Pet(4, 2, "Sozin", "Cat");
		Pet pet5 = new Pet(5, 4, "Zuko", "Parrot");

		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
		pets.add(pet4);
		pets.add(pet5);

		return Response.ok(pets).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pets",
					content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	@Path("/getPets")
	public Response getPets() {

		return Response.ok(pets).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id",
					content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@GET
	@Path("/getPet/{petId}")
	public Response getPet(@PathParam("petId") int petId) {
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Pet pet = new Pet();
		for (int i = 0;i<pets.size();i++){
			if(petId == pets.get(i).getPetId()){
				pet.setPetId(petId);
				pet.setPetAge(pets.get(i).getPetAge());
				pet.setPetName(pets.get(i).getPetName());
				pet.setPetType(pets.get(i).getPetType());
			}
		}

		return Response.ok(pet).build();
		
	}


	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id",
					content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@GET
	@Path("/searchByName/{petName}")
	public Response searchByName(@PathParam("petName") String petName) {

		Pet pet = new Pet();
		for (int i = 0;i<pets.size();i++){
			if(petName.equals(pets.get(i).getPetName())){
				pet.setPetId(pets.get(i).getPetId());
				pet.setPetAge(pets.get(i).getPetAge());
				pet.setPetName(pets.get(i).getPetName());
				pet.setPetType(pets.get(i).getPetType());
				break;
			}
		}

		return Response.ok(pet).build();

	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for age",
					content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/filterByAge")
	public Response filterByAge(@FormParam("petAge") int petAge) {

		List<Pet> filteredPets = new ArrayList<Pet>();

		for (int i = 0;i<pets.size();i++){
			if(petAge == pets.get(i).getPetAge()){
				Pet pet = new Pet();
				pet.setPetId(pets.get(i).getPetId());
				pet.setPetAge(pets.get(i).getPetAge());
				pet.setPetName(pets.get(i).getPetName());
				pet.setPetType(pets.get(i).getPetType());

				filteredPets.add(pet);
			}
		}

		return Response.ok(filteredPets).build();

	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for age",
					content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/filterByType")
	public Response filterByType(@FormParam("petType") String petType) {

		List<Pet> filteredPets = new ArrayList<Pet>();

		for (int i = 0;i<pets.size();i++){
			if(petType.equals(pets.get(i).getPetType())){
				Pet pet = new Pet();
				pet.setPetId(pets.get(i).getPetId());
				pet.setPetAge(pets.get(i).getPetAge());
				pet.setPetName(pets.get(i).getPetName());
				pet.setPetType(pets.get(i).getPetType());

				filteredPets.add(pet);
			}
		}

		return Response.ok(filteredPets).build();

	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Delete Pet for id",
					content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@GET
	@Path("/deletePet/{petId}")
	public Response deletePet(@PathParam("petId") int petId) {
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}

		for (int i = 0;i<pets.size();i++){
			if(petId == pets.get(i).getPetId()){
				pets.remove(i);
			}
		}

		return Response.ok(pets).build();

	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Add Pet",
					content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/addPet")
	public Response addPet(@FormParam("pet_id") String petId, @FormParam("pet_name") String petName,
						   @FormParam("pet_age") String petAge, @FormParam("pet_type") String petType) {
		if (Integer.parseInt(petId) < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}

		for (int i = 0;i<pets.size();i++){
			if(Integer.parseInt(petId) == pets.get(i).getPetId()){
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}
		}

		Pet pet = new Pet();
		pet.setPetId(Integer.parseInt(petId));
		pet.setPetAge(Integer.parseInt(petAge));
		pet.setPetName(petName);
		pet.setPetType(petType);

		pets.add(pet);

		return Response.ok(pets).build();

	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Add Pet",
					content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/updatePet")
	public Response updatePet(@FormParam("pet_id") String petId, @FormParam("pet_name") String petName,
						   @FormParam("pet_age") String petAge, @FormParam("pet_type") String petType) {
		if (Integer.parseInt(petId) < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}



		for (int i = 0;i<pets.size();i++){
			if(Integer.parseInt(petId) == pets.get(i).getPetId()){
				pets.get(i).setPetAge(Integer.parseInt(petAge));
				pets.get(i).setPetName(petName);
				pets.get(i).setPetType(petType);
				break;
			}
		}


		return Response.ok(pets).build();

	}

}
