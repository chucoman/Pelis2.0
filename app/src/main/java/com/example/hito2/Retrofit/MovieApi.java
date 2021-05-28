package com.example.hito2.Retrofit;

import com.example.hito2.Retrofit.models.MovieModel;
import com.example.hito2.Retrofit.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

//Search peliculas
    @GET(Credenciales.Base_URL +"/3/search/movie")
    Call<MovieSearchResponse> searchMovie(

            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );


    //Busqueda por id
    @GET(Credenciales.Base_URL + "/3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
        @Path("movie_id")int movie_id,
        @Query("api_key") String api_key);



}
