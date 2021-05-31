package com.example.hito2.Retrofit.viewmodels;

import android.app.Application;

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
    public MovieListViewModel() {
        movieRepositorio = MovieRepositorio.getInstance();
    }

    //buscar peliculas
    public LiveData<List<MovieModel>> getMovies(){
        return movieRepositorio.getMovies();
    }
    public void searchMovieApi(String query, int pageNumber){movieRepositorio.searchMovieApi(query,pageNumber);}


    public void searchNextpage(){movieRepositorio.searchNextPage();}


    //populares
    public LiveData<List<MovieModel>> getPop(){
        return movieRepositorio.getPopular();
    }
    public void searchMoviePopular(int pageNumber){movieRepositorio.searchMoviePopular(pageNumber);}

    //Top
    public LiveData<List<MovieModel>> getTop(){
        return movieRepositorio.getTop();
    }
    public void searchMovieTop(int pageNumber){movieRepositorio.searchMovieTop(pageNumber);}

    //proximos estrenos
    public LiveData<List<MovieModel>> getUpc(){
        return movieRepositorio.getUpc();
    }
    public void searchMovieUpc(int pageNumber){movieRepositorio.searchMovieUpc(pageNumber);}

    //Ya en cines
    public LiveData<List<MovieModel>> getNow(){
        return movieRepositorio.getNow();
    }
    public void searchMovieNow(int pageNumber){movieRepositorio.searchMovieNow(pageNumber);}



}
