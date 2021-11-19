package org.acme;

import com.example.petstore.model.Pet;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.wildfly.common.Assert;

import java.util.*;

import static com.example.petstore.data.DataStore.pets;
import static io.restassured.RestAssured.given;

@QuarkusTest
public class PetResourceTest {

	@Test
    public void testAddPetEndpoint() {
        Response response = given()
        .formParam("pet_id", "2300")
                .formParam("pet_name", "Trex")
                .formParam("pet_age", "1")
                .formParam("pet_type", "Crocodile")
          .when().post("/pets/addPet");

        Pet pet = new Pet();
        for (int i = 0;i<pets.size();i++){
            if(pets.get(i).getPetId()==2300){
                pet.setPetId(2300);
                pet.setPetAge(pets.get(i).getPetAge());
                pet.setPetName(pets.get(i).getPetName());
                pet.setPetType(pets.get(i).getPetType());
                break;
            }
        }

        Assert.assertTrue(pet.getPetType().equals("Crocodile"));
        Assert.assertTrue(pet.getPetAge()==1);
        Assert.assertTrue(pet.getPetName().equals("Trex"));

    }

    @Test
    public void testUpdatePetEndpoint() {
        given()
                .formParam("pet_id", "2300")
                .formParam("pet_name", "Trex")
                .formParam("pet_age", "1")
                .formParam("pet_type", "Crocodile")
                .when().post("/pets/addPet");

        given()
                .formParam("pet_id", "2300")
                .formParam("pet_name", "Lola")
                .formParam("pet_age", "2")
                .formParam("pet_type", "Moth")
                .when().post("/pets/updatePet");

        Pet pet = new Pet();
        for (int i = 0;i<pets.size();i++){
            if(pets.get(i).getPetId()==2300){
                pet.setPetId(2300);
                pet.setPetAge(pets.get(i).getPetAge());
                pet.setPetName(pets.get(i).getPetName());
                pet.setPetType(pets.get(i).getPetType());
                break;
            }
        }

        Assert.assertTrue(pet.getPetType().equals("Moth"));
        Assert.assertTrue(pet.getPetAge()==2);
        Assert.assertTrue(pet.getPetName().equals("Lola"));

    }


    @Test
    public void testDeletePetEndpoint() {
        given()
                .formParam("pet_id", "23")
                .formParam("pet_name", "Trex")
                .formParam("pet_age", "1")
                .formParam("pet_type", "Crocodile")
                .when().post("/pets/addPet");

        given()
                .when().post("/pets/deletePet/23");

        Pet pet = new Pet();
        for (int i = 0;i<pets.size();i++){
            if(pets.get(i).getPetId()==2300){
                pet.setPetId(2300);
                pet.setPetAge(pets.get(i).getPetAge());
                pet.setPetName(pets.get(i).getPetName());
                pet.setPetType(pets.get(i).getPetType());
                break;
            }
        }

        Assert.assertTrue(pet.getPetId()==null);

    }


    @Test
    public void testGetPetEndpoint() {

        given()
                .formParam("pet_id", "2300")
                .formParam("pet_name", "Trex")
                .formParam("pet_age", "1")
                .formParam("pet_type", "Crocodile")
                .when().post("/pets/addPet");

        Object pet = given()
                .when().get("/pets/getPet/2300")
                .then()
                .extract().response().body().path("");



        JSONObject json = new JSONObject((Map) pet);
        Assert.assertTrue(json.get("petName").equals("Trex"));
        Assert.assertTrue(json.get("petAge").equals(1));
        Assert.assertTrue(json.get("petType").equals("Crocodile"));


    }


    @Test
    public void testSearchPetByNameEndpoint() {

	    given()
                .when().get("/pets")
                .then()
                .extract().response().body().path("");

        Object pet = given()
                .when().get("/pets/searchByName/Princess")
                .then()
                .extract().response().body().path("");


        JSONObject json = new JSONObject((Map) pet);
        Assert.assertTrue(json.get("petId").equals(2));
        Assert.assertTrue(json.get("petName").equals("Princess"));
        Assert.assertTrue(json.get("petAge").equals(4));
        Assert.assertTrue(json.get("petType").equals("Cat"));

    }


    @Test
    public void testFilterPetsByAgeEndpoint() {

        given()
                .when().get("/pets")
                .then()
                .extract().response().body().path("");

        List<Object> returnedPets = given()
                .formParam("petAge", 4)
                .when().post("/pets/filterByAge")
                .then()
                .extract().response().body().path("");

//        List<JSONObject> returnedPets = new ArrayList<JSONObject>();
        for (Object obj:
                returnedPets) {
            JSONObject pet = new JSONObject((Map) obj);
            Assert.assertTrue(pet.get("petAge").equals(4));
        }


    }

    @Test
    public void testFilterPetsByTypeEndpoint() {

        given()
                .when().get("/pets")
                .then()
                .extract().response().body().path("");

        List<Object> returnedPets = given()
                .formParam("petType", "Cat")
                .when().post("/pets/filterByType")
                .then()
                .extract().response().body().path("");

//        List<JSONObject> returnedPets = new ArrayList<JSONObject>();
        for (Object obj:
                returnedPets) {
            JSONObject pet = new JSONObject((Map) obj);
            Assert.assertTrue(pet.get("petType").equals("Cat"));
        }


    }


    @Test
    public void testPetEndpoint() {
        List<Object> str = given()
                .when().get("/pets")
                .then()
                .extract().response().body().path("");
//             .body(hasItem(
// 		            allOf(
//    		                hasEntry("petId", "2300"),
//    		                hasEntry("petType", "Trex"),
//    		                hasEntry("petName", "Crocodile"),
//    		                hasEntry("petAge", "1")
//    		            )
//    		      )
//    		 );

        List<JSONObject> returnedPets = new ArrayList<JSONObject>();
        for (Object obj:
             str) {
            returnedPets.add(new JSONObject((Map) obj));
        }

        System.out.println(returnedPets);



    }

}