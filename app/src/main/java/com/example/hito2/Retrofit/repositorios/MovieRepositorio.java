package com.example.hito2.Retrofit.repositorios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hito2.Retrofit.models.MovieModel;
import com.example.hito2.Retrofit.request.MovieApiClient;

import java.util.List;

public class MovieRepositorio {

    private static MovieRepositorio instance;
    private MovieApiClient movieApiClient;
    private String mQuery;
    private int mPageNumber;


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

    //buscar pelicula
    public LiveData<List<MovieModel>> getMovies(){return movieApiClient.getMovies();}
    public void searchMovieApi(String query, int pageNumber){
    mQuery = query;
    mPageNumber = pageNumber;
    movieApiClient.searchMoviesApi(query, pageNumber);

    }


    //siguiente pagina
    public void searchNextPage(){
        searchMovieApi(mQuery, mPageNumber+1);
    }



    //populares
    public LiveData<List<MovieModel>> getPopular(){return movieApiClient.getMoviesPopular();}
    public void searchMoviePopular(int pageNumber){

        mPageNumber = pageNumber;
        movieApiClient.searchMoviesPopular(pageNumber);

    }

    //top
    public LiveData<List<MovieModel>> getTop(){return movieApiClient.getMoviesTop();}
    public void searchMovieTop(int pageNumber){

        mPageNumber = pageNumber;
        movieApiClient.searchMoviesTop(pageNumber);

    }

    //Proximos estrenos
    public LiveData<List<MovieModel>> getUpc(){return movieApiClient.getMoviesUpcoming();}
    public void searchMovieUpc(int pageNumber){

        mPageNumber = pageNumber;
        movieApiClient.searchMoviesUpc(pageNumber);

    }

    //Ya en cines
    public LiveData<List<MovieModel>> getNow(){return movieApiClient.getMoviesNow();}
    public void searchMovieNow(int pageNumber){

        mPageNumber = pageNumber;
        movieApiClient.searchMoviesNow(pageNumber);

    }


}
