package com.example.hito2.Retrofit.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hito2.Retrofit.models.MovieModel;
import com.example.hito2.Retrofit.repositorios.MovieRepositorio;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    //esta clase es usada por el View model


    private MovieRepositorio movieRepositorio;




    //constructor


    public MovieListViewModel(MutableLiveData<List<MovieModel>> mMovies) {
        movieRepositorio = MovieRepositorio.getInstance();
    }


    public LiveData<List<MovieModel>> getMovies(){
        return movieRepositorio.getMovies();
    }


}
