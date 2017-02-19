package com.lostandspotted;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.lostandspotted.api.LostAndSpottedService;
import com.lostandspotted.api.PetRequest;
import com.lostandspotted.models.Image;
import com.lostandspotted.models.Pet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostPetDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    List<Image> images;
    Pet pet;

    LostAndSpottedService.Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_pet_details);

        pet = new Pet();
        images = new ArrayList<>();
        Image image = (Image) getIntent().getSerializableExtra("IMAGE");

        if (image != null) {
            images.add(image);
        }

        client = LostAndSpottedService.getClient();

        initSpinner(R.id.spinner_pet_type);
        initSpinner(R.id.spinner_pet_size);
        initSpinner(R.id.spinner_pet_hair_length);

        findViewById(R.id.button_submit).setOnClickListener(this);
    }

    public void initSpinner(int id) {
        ArrayAdapter<CharSequence> adapter;
        Spinner spinner = (Spinner) findViewById(id);

        if (id == R.id.spinner_pet_type) {
            adapter = ArrayAdapter.createFromResource(this, R.array.pet_type_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        } else if (id == R.id.spinner_pet_size) {
            adapter = ArrayAdapter.createFromResource(this, R.array.pet_size_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        } else if (id == R.id.spinner_pet_hair_length) {
            adapter = ArrayAdapter.createFromResource(this, R.array.pet_hair_length, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        } else {
            adapter = null;
        }

        if (adapter != null) {
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String value = parent.getItemAtPosition(pos).toString();

        if (parent.getId() == R.id.spinner_pet_type) {
            pet.setPetType(value);
        } else if (parent.getId() == R.id.spinner_pet_size) {
            pet.setSize(value);
        } else if (parent.getId() == R.id.spinner_pet_hair_length) {
            pet.setHairLength(value);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void createPet(Pet pet) {
        Call<Pet> call = client.createPet(new PetRequest(pet));
        call.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                updatePet(response.body());
                createPetImages(response.body().getId(), images);
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Toast.makeText(PostPetDetailsActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatePet(Pet pet) {
        this.pet = pet;
    }

    public void createPetImages(int petId, List<Image> images) {
        for (Image image : images) {
            Call<Image> call = client.createImage(petId, image);
            call.enqueue(new Callback<Image>() {
                @Override
                public void onResponse(Call<Image> call, Response<Image> response) {
                    updatePetImage(response.body());
                }

                @Override
                public void onFailure(Call<Image> call, Throwable t) {
                    Toast.makeText(PostPetDetailsActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void updatePetImage(Image image) {
        pet.setImage(image);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_submit) {
            createPet(pet);
        }
    }
}
