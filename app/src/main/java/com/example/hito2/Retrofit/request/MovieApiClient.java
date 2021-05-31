package com.example.hito2.Retrofit.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hito2.Retrofit.AppExecutors;
import com.example.hito2.Retrofit.utilidades.Credenciales;
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

    // LiveData
    private MutableLiveData<List<MovieModel>> mMovies ;
    private static MovieApiClient instance;

    // making Global RUNNABLE
    private RetrieveMoviesRunnable retrieveMoviesRunnable;


    private MutableLiveData<List<MovieModel>> mMoviesPopular;
    private RetrieveMoviesRunnablePop retrieveMoviesRunnablePopular;

    private MutableLiveData<List<MovieModel>> mMoviesTop;
    private RetrieveMoviesRunnableTop retrieveMoviesRunnableTop;

    private MutableLiveData<List<MovieModel>> mMoviesUpcoming;
    private RetrieveMoviesRunnableUpc retrieveMoviesRunnableUpcoming;

    private MutableLiveData<List<MovieModel>> mMoviesNow;
    private RetrieveMoviesRunnableNow retrieveMoviesRunnableNow;


    public static MovieApiClient getInstance(){
        if (instance == null){
            instance = new MovieApiClient();
        }
        return  instance;

    }

    private MovieApiClient(){

        mMovies = new MutableLiveData<>();
        mMoviesPopular = new MutableLiveData<>();
        mMoviesTop = new MutableLiveData<>();
        mMoviesUpcoming = new MutableLiveData<>();
        mMoviesNow = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }
    public LiveData<List<MovieModel>> getMoviesPopular(){
        return mMoviesPopular;
    }
    public LiveData<List<MovieModel>> getMoviesTop(){
        return mMoviesTop;
    }
    public LiveData<List<MovieModel>> getMoviesUpcoming(){
        return mMoviesUpcoming;
    }
    public LiveData<List<MovieModel>> getMoviesNow(){
        return mMoviesNow;
    }


    //1
    public void searchMoviesApi(String query, int pageNumber) {

        if (retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                myHandler.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);


    }

    public void searchMoviesPopular(int pageNumber) {

        if (retrieveMoviesRunnablePopular != null){
            retrieveMoviesRunnablePopular = null;
        }

        retrieveMoviesRunnablePopular = new RetrieveMoviesRunnablePop(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnablePopular);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                myHandler2.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);


    }

    public void searchMoviesTop(int pageNumber) {

        if (retrieveMoviesRunnableTop != null){
            retrieveMoviesRunnableTop = null;
        }

        retrieveMoviesRunnableTop = new RetrieveMoviesRunnableTop(pageNumber);

        final Future myHandler3 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnableTop);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                myHandler3.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);


    }

    public void searchMoviesUpc(int pageNumber) {

        if (retrieveMoviesRunnableUpcoming != null){
            retrieveMoviesRunnableUpcoming = null;
        }

        retrieveMoviesRunnableUpcoming = new RetrieveMoviesRunnableUpc(pageNumber);

        final Future myHandler4 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnableUpcoming);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                myHandler4.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);


    }

    public void searchMoviesNow(int pageNumber) {

        if (retrieveMoviesRunnableNow != null){
            retrieveMoviesRunnableNow = null;
        }

        retrieveMoviesRunnableNow = new RetrieveMoviesRunnableNow(pageNumber);

        final Future myHandler5 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnableNow);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                myHandler5.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);


    }




    // recuperacion datos de restApi
    private class RetrieveMoviesRunnable implements Runnable{

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


            try{

                Response response = getMovies(query, pageNumber).execute();

                if (cancelRequest) {

                    return;
                }
                if (response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if (pageNumber == 1){

                        mMovies.postValue(list);

                    }else{
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);



                    }

                }else{
                    String error = response.errorBody().string();
                    Log.v("Tagy", "Error " +error);
                    mMovies.postValue(null);

                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);

            }




        }

        // Search Method/ query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
            return Servicey.getMovieApi().searchMovie(
                    Credenciales.API_KEY,
                    query,
                    pageNumber

            );

        }
        private void cancelRequest(){
            Log.v("Tag", "Cancelación de la solicitud de búsqueda" );
            cancelRequest = true;
        }





    }

    private class RetrieveMoviesRunnablePop implements Runnable{
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePop(int pageNumber){
            this.pageNumber = pageNumber;
            cancelRequest = false;

        }

        @Override
        public void run() {
            try{
                Response response2 = getPop(pageNumber).execute();
                if (cancelRequest) {

                    return;
                }
                if (response2.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response2.body()).getMovies());
                    if (pageNumber == 1){
                        // Se envian datos en vivo
                        // PostValue: usado par ael hilo de fondo
                       mMoviesPopular.postValue(list);



                    }else{
                        List<MovieModel> currentMovies = mMoviesPopular.getValue();
                        currentMovies.addAll(list);
                        mMoviesPopular.postValue(currentMovies);



                    }

                }else{
                    String error = response2.errorBody().string();
                    Log.v("Tagy", "Error: " +error);
                    mMoviesPopular.postValue(null);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        private Call<MovieSearchResponse> getPop(int pageNumber){
            return Servicey.getMovieApi().getPopular(
                    Credenciales.API_KEY,
                    pageNumber
            );
        }
        private void cancelRequest(){
            Log.v("Tag", "Cancelación de la solicitud de búsqueda" );
            cancelRequest = true;
        }
    }

    private class RetrieveMoviesRunnableTop implements Runnable{
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnableTop(int pageNumber){
            this.pageNumber = pageNumber;
            cancelRequest = false;

        }

        @Override
        public void run() {
            try{
                Response response3 = getTop(pageNumber).execute();
                if (cancelRequest) {

                    return;
                }
                if (response3.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response3.body()).getMovies());
                    if (pageNumber == 1){
                        // Se envian datos en vivo
                        // PostValue: usado par ael hilo de fondo
                        mMoviesTop.postValue(list);



                    }else{
                        List<MovieModel> currentMovies = mMoviesTop.getValue();
                        currentMovies.addAll(list);
                        mMoviesTop.postValue(currentMovies);



                    }

                }else{
                    String error = response3.errorBody().string();
                    Log.v("Tagy", "Error: " +error);
                    mMoviesTop.postValue(null);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        private Call<MovieSearchResponse> getTop(int pageNumber){
            return Servicey.getMovieApi().getTopRated(
                    Credenciales.API_KEY,
                    pageNumber
            );
        }
        private void cancelRequest(){
            Log.v("Tag", "Cancelación de la solicitud de búsqueda" );
            cancelRequest = true;
        }
    }

    private class RetrieveMoviesRunnableUpc implements Runnable{
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnableUpc(int pageNumber){
            this.pageNumber = pageNumber;
            cancelRequest = false;

        }

        @Override
        public void run() {
            try{
                Response response4 = getUpc(pageNumber).execute();
                if (cancelRequest) {

                    return;
                }
                if (response4.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response4.body()).getMovies());
                    if (pageNumber == 1){
                        // Se envian datos en vivo
                        // PostValue: usado par ael hilo de fondo
                        mMoviesUpcoming.postValue(list);



                    }else{
                        List<MovieModel> currentMovies = mMoviesUpcoming.getValue();
                        currentMovies.addAll(list);
                        mMoviesUpcoming.postValue(currentMovies);



                    }

                }else{
                    String error = response4.errorBody().string();
                    Log.v("Tagy", "Error: " +error);
                    mMoviesUpcoming.postValue(null);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        private Call<MovieSearchResponse> getUpc(int pageNumber){
            return Servicey.getMovieApi().getUpcoming(
                    Credenciales.API_KEY,
                    pageNumber
            );
        }
        private void cancelRequest(){
            Log.v("Tag", "Cancelación de la solicitud de búsqueda" );
            cancelRequest = true;
        }
    }

    private class RetrieveMoviesRunnableNow implements Runnable{
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnableNow(int pageNumber){
            this.pageNumber = pageNumber;
            cancelRequest = false;

        }

        @Override
        public void run() {
            try{
                Response response5 = getNow(pageNumber).execute();
                if (cancelRequest) {

                    return;
                }
                if (response5.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response5.body()).getMovies());
                    if (pageNumber == 1){
                        // Se envian datos en vivo
                        // PostValue: usado par ael hilo de fondo
                        mMoviesNow.postValue(list);



                    }else{
                        List<MovieModel> currentMovies = mMoviesNow.getValue();
                        currentMovies.addAll(list);
                        mMoviesNow.postValue(currentMovies);



                    }

                }else{
                    String error = response5.errorBody().string();
                    Log.v("Tagy", "Error: " +error);
                    mMoviesNow.postValue(null);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        private Call<MovieSearchResponse> getNow(int pageNumber){
            return Servicey.getMovieApi().getNowPlaying(
                    Credenciales.API_KEY,
                    pageNumber
            );
        }
        private void cancelRequest(){
            Log.v("Tag", "Cancelación de la solicitud de búsqueda" );
            cancelRequest = true;
        }
    }





}
