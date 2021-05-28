package com.example.hito2.Retrofit.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hito2.Retrofit.AppExecutors;
import com.example.hito2.Retrofit.Credenciales;
import com.example.hito2.Retrofit.models.MovieModel;
import com.example.hito2.Retrofit.response.MovieSearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {
    //livedata
    private MutableLiveData<List<MovieModel>> mMovies = new MutableLiveData<>();
    private static MovieApiClient instance;

    //solicitud ejecutable global
    private RetrieveMoviesRunnable retrieveMoviesRunnable;




    public static MovieApiClient getInstance(){
        if(instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }


    private MovieApiClient(){mMovies = new MutableLiveData<>();}

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }



    public void searchMoviesApi(String query, int pageNumber){
        if(retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }
        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
             //cancelar llamada retrofit
                myHandler.cancel(true);
            }
        },4000, TimeUnit.MICROSECONDS);


    }


    //recupera API mediante runable

    private class RetrieveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
                //getting los objetos de respuesta
            try{
                Response response = getMovies(query,pageNumber).execute();

                if(cancelRequest){
                    return;
                }
                if(response.code()==200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber ==1){
                        mMovies.postValue(list);

                    }else{
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                     }
                }else{
                    String error = response.errorBody().toString();
                    Log.v("Tag","Error " + error);
                    mMovies.postValue(null);
                }



            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }

            if(cancelRequest){
                return;
            }

        }
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
            return Servicey.getMovieApi().searchMovie(
                    Credenciales.API_KEY,
                    query, pageNumber

            );

        }
        private void cancelRequest(){
            Log.v("Tag","Cancelar solicitud de busqueda");
            cancelRequest = true;
        }

    }




}
