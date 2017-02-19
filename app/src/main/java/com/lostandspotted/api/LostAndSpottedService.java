package com.lostandspotted.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lostandspotted.models.Comment;
import com.lostandspotted.models.Image;
import com.lostandspotted.models.Pet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class LostAndSpottedService {
//    private final static String BASE_API_URL = "https://lost-and-spotted.herokuapp.com/api/";
    private final static String BASE_API_URL = "http://192.168.2.235:3000/api/";
    private static Client client = null;

    public static Client getClient() {
        if (client == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            client = retrofit.create(Client.class);
        }

        return client;
    }

    public interface Client {
        /* === Pets === */
        // Index
        @GET("pets")
        Call<List<Pet>> getPets(@Query("page") int page);

        // Show
        @GET("pets/{id}")
        Call<Pet> getPet(@Path("id") int id);

        // Create
        @POST("pets")
        Call<Pet> createPet(@Body PetRequest pet);

        // Update
        @PUT("pets/{id}")
        Call<Pet> updatePet(@Path("id") int id, @Body Pet pet);


        /* === Pets/Comments === */
        // Create
        @POST("pets/{pet_id}/comments")
        Call<Comment> createComment(@Body Comment comment);

        // Destroy
        @DELETE("pets/{pet_id}/comments/{id}")
        Call<Comment> deleteComment(@Path("id") int id);


        /* === Pet/Images === */
        // Create
        @POST("pets/{pet_id}/images")
        Call<Image> createImage(@Path("pet_id") int petId, @Body Image image);
    }
}
