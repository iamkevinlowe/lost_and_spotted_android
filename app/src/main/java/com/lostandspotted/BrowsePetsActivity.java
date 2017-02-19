package com.lostandspotted;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.lostandspotted.api.LostAndSpottedService;
import com.lostandspotted.models.Pet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowsePetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_pets);

        ListView listView = (ListView) findViewById(R.id.listview_pets_browse);
        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemCount) {
                loadNextPets(page);
                return true;
            }
        });
    }

    public void loadNextPets(int page) {
        LostAndSpottedService.Client client = LostAndSpottedService.getClient();
        Call<List<Pet>> call = client.getPets(page);
        call.enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                for (Pet pet : response.body()) {
                    Log.d("lost_and_spotted", String.valueOf(pet.getId()));
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                Toast.makeText(BrowsePetsActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
