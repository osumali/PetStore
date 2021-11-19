package com.example.petstore.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

@Schema(name = "PetType")
public class PetType {

    @Schema(required = true, description = "Pet id")
    @JsonProperty("id")
    private Integer id;

    @Schema(required = true, description = "Pet type")
    @JsonProperty("type")
    private String type;

    public PetType(){}

    public PetType(Integer id, String type){
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
