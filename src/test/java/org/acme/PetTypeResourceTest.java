package org.acme;

import com.example.petstore.model.PetType;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.wildfly.common.Assert;

import java.util.*;

import static com.example.petstore.data.DataStore.petTypes;
import static io.restassured.RestAssured.given;

@QuarkusTest
public class PetTypeResourceTest {

    @Test
    public void testAddPetTypeEndpoint() {
        Response response = given()
                .formParam("id", 23)
                .formParam("type", "Crocodile")
                .when().post("/petTypes/addPetType");

        PetType petType = new PetType();
        for (int i = 0;i<petTypes.size();i++){
            if(petTypes.get(i).getId()==23){
                petType.setId(23);
                petType.setType(petTypes.get(i).getType());
                break;
            }
        }

        Assert.assertTrue(petType.getType().equals("Crocodile"));

    }

    @Test
    public void testUpdatePetEndpoint() {
        given()
                .when().get("/petTypes")
                .then()
                .extract().response().body().path("");

        given()
                .formParam("id", 2)
                .formParam("type", "Moth")
                .when().post("/petTypes/updatePetType");

        PetType petType = new PetType();
        for (int i = 0;i<petTypes.size();i++){
            if(petTypes.get(i).getId()==2){
                petType.setId(2);
                petType.setType(petTypes.get(i).getType());
                break;
            }
        }

        Assert.assertTrue(petType.getType().equals("Moth"));

    }


    @Test
    public void testDeletePetEndpoint() {
        given()
                .formParam("id", 6)
                .formParam("type", "Toad")
                .when().post("/petTypes/addPetType");

        given()
                .when().get("/petTypes/deletePetType/6");

        PetType petType = new PetType();
        for (int i = 0;i<petTypes.size();i++){
            if(petTypes.get(i).getId()==6){
                petType.setId(6);
                petType.setType(petTypes.get(i).getType());
                break;
            }
        }

        Assert.assertTrue(petType.getId()==null);

    }


    @Test
    public void testGetPetTypeEndpoint() {

        given()
                .when().get("/petTypes")
                .then()
                .extract().response().body().path("");

        Object petType = given()
                .when().get("/petTypes/getPetType/2")
                .then()
                .extract().response().body().path("");



        JSONObject json = new JSONObject((Map) petType);
        Assert.assertTrue(json.get("type").equals("Cat"));


    }


    @Test
    public void testPetTypeEndpoint() {
        List<Object> str = given()
                .when().get("/petTypes")
                .then()
                .extract().response().body().path("");

        List<JSONObject> returnedPetTypes = new ArrayList<JSONObject>();
        for (Object obj:
                str) {
            returnedPetTypes.add(new JSONObject((Map) obj));
        }

        System.out.println(returnedPetTypes);



    }

}