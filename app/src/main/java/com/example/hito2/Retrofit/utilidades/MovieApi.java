package com.example.hito2.Retrofit.utilidades;

import com.example.hito2.Retrofit.models.MovieModel;
import com.example.hito2.Retrofit.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {


    //Search peliculas
    //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Matrix
    @GET("3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    //get peliculas populares
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page") int page

    );

    //get peliculas top votaciones
    //https://api.themoviedb.org/3/movie/top_rated?api_key=3bb1f59db17ac605ef7cdd6d42a32cd7
    @GET("/3/movie/top_rated")
    Call<MovieSearchResponse> getTopRated(
            @Query("api_key") String key,
            @Query("page") int page

    );


    //proximas peliculas en cine
    //https://api.themoviedb.org/3/movie/upcoming?api_key=3bb1f59db17ac605ef7cdd6d42a32cd7
    @GET("/3/movie/upcoming")
    Call<MovieSearchResponse> getUpcoming(
            @Query("api_key") String key,
            @Query("page") int page

    );


    //peliculas ya en cines
    //https://api.themoviedb.org/3/movie/now_playing?api_key=3bb1f59db17ac605ef7cdd6d42a32cd7
    @GET("/3/movie/upcoming")
    Call<MovieSearchResponse> getNowPlaying(
            @Query("api_key") String key,
            @Query("page") int page

    );


    //Busqueda por id
    // https://api.themoviedb.org/3/movie/550?api_key=3bb1f59db17ac605ef7cdd6d42a32cd7
    @GET("3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
        @Path("movie_id")int movie_id,
        @Query("api_key") String api_key);



}
