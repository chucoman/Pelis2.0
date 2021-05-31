package com.example.hito2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hito2.Adaptadores.MovieAdaptadorRetrofit2;
import com.example.hito2.Adaptadores.OnMovieListener;
import com.example.hito2.R;
import com.example.hito2.Retrofit.Detalle;
import com.example.hito2.Retrofit.models.MovieModel;
import com.example.hito2.Retrofit.request.Servicey;
import com.example.hito2.Retrofit.response.MovieSearchResponse;
import com.example.hito2.Retrofit.utilidades.Credenciales;
import com.example.hito2.Retrofit.utilidades.MovieApi;
import com.example.hito2.Retrofit.viewmodels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.google.gson.reflect.TypeToken.get;

public class SearchResponse extends AppCompatActivity implements OnMovieListener {
    //recryclerView
    private RecyclerView recyclerView;
    private MovieAdaptadorRetrofit2 movieAdaptadorRetrofit2;
    //ViewModel
    private MovieListViewModel movieListViewModel;

    boolean esPopular = true;
    boolean esTop = true;
    boolean esUpc = true;
    boolean esNow = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_response);
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recyclerRetro);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ObserveAnyChange();
        busquedaView();
        ConfigureRecyclerView();
        ObservePopularMovies();
        movieListViewModel.searchMoviePopular(1);

       //ObserveTopMovies();
       //ObserveUpcMovies();
       //ObserveNowMovies();

      // movieListViewModel.searchMovieTop(1);
      // movieListViewModel.searchMovieUpc(1);
      // movieListViewModel.searchMovieNow(1);






    }

    private void ObservePopularMovies() {
        movieListViewModel.getPop().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //para cualquier cambio
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        //obtenemos  datos
                        Log.v("Tag","onChanged" +movieModel.getTitle());
                        movieAdaptadorRetrofit2.setmMovies(movieModels);
                    }
                }

            }
        });
    }

    private void ObserveTopMovies() {
        movieListViewModel.getTop().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //para cualquier cambio
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        //obtenemos  datos
                        Log.v("Tag","onChanged" +movieModel.getTitle());
                        movieAdaptadorRetrofit2.setmMovies(movieModels);
                    }
                }

            }
        });
    }

    private void ObserveUpcMovies() {
        movieListViewModel.getUpc().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //para cualquier cambio
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        //obtenemos  datos
                        Log.v("Tag","onChanged" +movieModel.getTitle());
                        movieAdaptadorRetrofit2.setmMovies(movieModels);
                    }
                }

            }
        });
    }

    private void ObserveNowMovies() {
        movieListViewModel.getNow().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //para cualquier cambio
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        //obtenemos  datos
                        Log.v("Tag","onChanged" +movieModel.getTitle());
                        movieAdaptadorRetrofit2.setmMovies(movieModels);
                    }
                }

            }
        });
    }


    //observar cambios en data
    private void ObserveAnyChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //para cualquier cambio
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        //obtenemos  datos
                        Log.v("Tag","onChanged" +movieModel.getTitle());
                        movieAdaptadorRetrofit2.setmMovies(movieModels);
                    }
                }

            }
        });

    }
    //paso 4
    private void searchMovieApi(String query, int pageNumber){
        movieListViewModel.searchMovieApi(query,pageNumber);
    }


    //inicializar recycler view y añadir datos
    private void ConfigureRecyclerView(){
        movieAdaptadorRetrofit2 = new MovieAdaptadorRetrofit2( this);

        recyclerView.setAdapter(movieAdaptadorRetrofit2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        //recyclerview paginacion
        //carga siguiente pagina
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){
                    // Here we need to display the next search results on the next page of api
                    movieListViewModel.searchNextpage();

                }

            }
        });

    }







    private void GetRetrofitResponse(){
        MovieApi movieApi = Servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi
                .searchMovie(
                        Credenciales.API_KEY,
                        "Fast",
                        1
                );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code()== 200){
                    Log.v("Tag","Respuesta " +response.body().toString()+"\n");
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                    for(MovieModel movie: movies){
                        Log.v("Tag", "La Pelicula se hizo " +movie.getRelease_date());
                    }
                }
                else{
                    try{
                        Log.v("Tag", "Error " +response.errorBody().toString());
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

    private void GetRetrofitResponseId(){

        MovieApi movieApi = Servicey.getMovieApi();
        Call<MovieModel> responseCall = movieApi.getMovie(
                550,
                Credenciales.API_KEY);
        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.code()==200){
                    MovieModel movie = response.body();
                    Log.v("Tag", "el titulo es "+ movie.getTitle());
                }
                else{
                    try{
                        Log.v("Tag", "Error " +response.errorBody().toString());
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


    @Override
    public void onMovieClick(int posicion) {
        Intent intent = new Intent(this, Detalle.class);
        intent.putExtra("pelicula", movieAdaptadorRetrofit2.getSelectedMovie(posicion));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    private void busquedaView(){
        final androidx.appcompat.widget.SearchView searchView = findViewById(R.id.search_view);

        //detectar busqueda
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do what you want when search view expended
                esPopular = false;
                Log.v("Tagy", "ispop: " +esPopular);

            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //do what you want  searchview is not expanded
                return false;
            }
        });




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(
                        query,
                        1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }
}