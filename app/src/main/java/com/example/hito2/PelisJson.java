package com.example.hito2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hito2.Adaptadores.Adapteryretrofit1;
import com.example.hito2.Retrofit.models.MovieModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PelisJson extends AppCompatActivity {
    //Json Link de TMDB
    int pageNum = 1;
    private  String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=3bb1f59db17ac605ef7cdd6d42a32cd7&page=";

    List<MovieModelClass> movieList;
    ImageView cabecera;
    RecyclerView recyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelis_json);

        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerJson);
        cabecera = findViewById(R.id.imageView3);
        Glide.with(this)
                .load(R.drawable.logo2_small)
                .into(cabecera);

        GetData getData = new GetData();
        getData.execute();

    }
    public class GetData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            String current="";

            try{
                URL url;
                HttpURLConnection urlConnection = null;

                try{
                url = new URL(JSON_URL+pageNum);
                Log.v("Tag","url"+ JSON_URL+pageNum);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream is = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                int data = isr.read();
                while(data != -1){
                    current += (char) data;
                    data = isr.read();
                }
                return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            try{
                JSONObject jsonObject = new JSONObject(s);

                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for(int i = 0; i < jsonArray.length();i++){

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    MovieModelClass model = new MovieModelClass();
                    model.setId(jsonObject1.getString("vote_average"));
                    model.setName(jsonObject1.getString("title"));
                    model.setImagen(jsonObject1.getString("poster_path"));

                    movieList.add(model);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(movieList);

        }
    }

    private void PutDataIntoRecyclerView(List<MovieModelClass> movieList){
        Adapteryretrofit1 adapteryretrofit1 = new Adapteryretrofit1(this, movieList);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(adapteryretrofit1);
    }


}