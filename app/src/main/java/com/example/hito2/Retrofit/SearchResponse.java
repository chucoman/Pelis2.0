package com.example.hito2.Retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hito2.R;
import com.example.hito2.Retrofit.models.MovieModel;
import com.example.hito2.Retrofit.request.Servicey;
import com.example.hito2.Retrofit.response.MovieSearchResponse;
import com.example.hito2.Retrofit.viewmodels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResponse extends AppCompatActivity {
    Button btn;

    //ViewModel
    private MovieListViewModel movieListViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_response);
        btn = findViewById(R.id.button2);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetRetrofitResponseSegunId();
            }
        });

    }
    //observar cambios en data
    private void ObserveAnyChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //para cualquier cambio


            }
        });

    }


        //metodo buscar peliculas
    private void GetRetrofitResponse() {
        MovieApi movieApi = Servicey.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi
                .searchMovie(
                        Credenciales.API_KEY,
                        "Matrix",
                        1);
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code()==200){
                    Log.v("Tag","la respuesta " + response.body().toString());

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for(MovieModel movie: movies){
                        Log.v("Tag","la lista "+ movie.getTitle());


                    }
                    }else{
                        try{
                            Log.v("Tag","Error" + response.errorBody().toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });

    }

        //metodo buscar pelicula por id
    private void GetRetrofitResponseSegunId(){
    MovieApi movieApi = Servicey.getMovieApi();
    Call<MovieModel> responseCall = movieApi
            .getMovie(
                    343611,
                    Credenciales.API_KEY);
    responseCall.enqueue( new Callback<MovieModel>() {
        @Override
        public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
            if(response.code()==200){
                 MovieModel movie = response.body();
                 Log.v("Tag","La respuesta es " + movie.getTitle());
            }else{
                try{
                    Log.v("Tag", "ERROR" + response.errorBody().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<MovieModel> call, Throwable t) {

        }
    });


    }



}