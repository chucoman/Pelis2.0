package com.example.hito2.Retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hito2.R;
import com.example.hito2.Retrofit.models.MovieModel;

public class Detalle extends AppCompatActivity {

    private ImageView imageViewDetalle;
    private TextView tituloDetalle, descDetalle;
    private RatingBar ratingBarDetalle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        imageViewDetalle = findViewById(R.id.imageViewDetalle);
        tituloDetalle = findViewById(R.id.textViewTituloDetalle);
        descDetalle = findViewById(R.id.textView_descDetalle);
        ratingBarDetalle = findViewById(R.id.ratingBarDetalle);
         GetDatos();

    }


    //datos del Intent
    private void GetDatos(){
        if(getIntent().hasExtra("pelicula")){
            MovieModel movieModel = getIntent().getParcelableExtra("pelicula");

            tituloDetalle.setText(movieModel.getTitle());
            descDetalle.setText(movieModel.getMovie_overview());
            ratingBarDetalle.setRating((movieModel.getVote_average())/2);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/"+
                            movieModel.getPoster_path())
            .into(imageViewDetalle);


        }
    }
}