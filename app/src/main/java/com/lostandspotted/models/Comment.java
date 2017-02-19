package com.lostandspotted.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comment implements Serializable {

    private int id;
    @SerializedName("pet_id")
    private int petId;
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}