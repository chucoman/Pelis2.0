package com.example.hito2.Retrofit.request;

import com.example.hito2.Retrofit.utilidades.Credenciales;
import com.example.hito2.Retrofit.utilidades.MovieApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Servicey {


    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
        .baseUrl(Credenciales.Base_URL)
        .addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit = retrofitBuilder.build();

    private static MovieApi movieApi = retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi(){

        return movieApi;
    }


}
