package com.example.hito2.Retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hito2.Conexion.ConexionSqliteHelper;
import com.example.hito2.R;
import com.example.hito2.Retrofit.models.MovieModel;

public class Detalle extends AppCompatActivity {

    private ImageView imageViewDetalle;
    private TextView tituloDetalle, descDetalle;
    private RatingBar ratingBarDetalle;
    private ConexionSqliteHelper dbHelper;
    private Button btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        dbHelper = new ConexionSqliteHelper(this);
        imageViewDetalle = findViewById(R.id.imageViewDetalle);
        tituloDetalle = findViewById(R.id.textViewTituloDetalle);
        descDetalle = findViewById(R.id.textView_descDetalle);
        ratingBarDetalle = findViewById(R.id.ratingBarDetalle);
        btn = findViewById(R.id.button2);

         GetDatos();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorita();

            }
        });

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

    public void favorita(){
        if(getIntent().hasExtra("pelicula")){
            MovieModel movieModel = getIntent().getParcelableExtra("pelicula");

                String titulo, fecha, imagen, descript;

                titulo = movieModel.getTitle();
                fecha = movieModel.getRelease_date();
                imagen = "https://image.tmdb.org/t/p/w500/"+movieModel.getPoster_path();
                descript = movieModel.getMovie_overview();

                try{
                    long id = dbHelper.insertPelicula(""+titulo,"",""+fecha,""+imagen,""+descript);



                } catch (Exception e) {
                    e.printStackTrace();
                }


        }
    }
}