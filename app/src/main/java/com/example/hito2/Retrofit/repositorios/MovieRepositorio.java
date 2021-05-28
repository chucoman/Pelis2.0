package com.example.hito2.Retrofit.repositorios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hito2.Retrofit.MovieApi;
import com.example.hito2.Retrofit.models.MovieModel;
import com.example.hito2.Retrofit.request.MovieApiClient;

import java.util.List;

public class MovieRepositorio {

    private static MovieRepositorio instance;
    private MovieApiClient movieApiClient;


    public static MovieRepositorio getInstance()
    {
        if(instance == null){
            instance = new MovieRepositorio();
        }
        return instance;
    }
private MovieRepositorio(){

    movieApiClient = MovieApiClient.getInstance();

}

public LiveData<List<MovieModel>> getMovies(){


        return movieApiClient.getMovies();
    }

}
