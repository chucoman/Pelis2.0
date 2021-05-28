package com.example.hito2.Retrofit.response;

import com.example.hito2.Retrofit.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//esta clase es para solicitar una sola pelicula
public class MovieResponse {
   @SerializedName("results")
   @Expose
   private MovieModel movie;

   public MovieModel getMovie(){
       return movie;
   }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
