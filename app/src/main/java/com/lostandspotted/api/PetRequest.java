package com.lostandspotted.api;

import com.lostandspotted.models.Pet;

public class PetRequest {
    private Pet pet;

    public PetRequest(Pet pet) {
        this.pet = pet;
    }
}
