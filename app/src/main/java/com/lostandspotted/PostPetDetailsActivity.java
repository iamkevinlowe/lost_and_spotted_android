package com.lostandspotted;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PostPetDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerPetType;
    Spinner spinnerPetSize;
    Spinner spinnerPetHairLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_pet_details);

        initSpinner(R.id.spinner_pet_type);
        initSpinner(R.id.spinner_pet_size);
        initSpinner(R.id.spinner_pet_hair_length);
    }

    public void initSpinner(int id) {
        ArrayAdapter<CharSequence> adapter;

        if (id == R.id.spinner_pet_type) {
            spinnerPetType = (Spinner) findViewById(id);
            adapter = ArrayAdapter.createFromResource(this, R.array.pet_type_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPetType.setAdapter(adapter);
            spinnerPetType.setOnItemSelectedListener(this);
        } else if (id == R.id.spinner_pet_size) {
            spinnerPetSize = (Spinner) findViewById(id);
            adapter = ArrayAdapter.createFromResource(this, R.array.pet_size_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPetSize.setAdapter(adapter);
            spinnerPetSize.setOnItemSelectedListener(this);
        } else if (id == R.id.spinner_pet_hair_length) {
            spinnerPetHairLength = (Spinner) findViewById(id);
            adapter = ArrayAdapter.createFromResource(this, R.array.pet_hair_length, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPetHairLength.setAdapter(adapter);
            spinnerPetHairLength.setOnItemSelectedListener(this);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
