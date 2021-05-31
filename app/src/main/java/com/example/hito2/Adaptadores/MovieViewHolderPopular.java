package com.example.hito2.Adaptadores;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hito2.R;

public class MovieViewHolderPopular extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView imageView_pop;
    RatingBar ratingBar_pop;
    OnMovieListener listener;


    public MovieViewHolderPopular(@NonNull View itemView, OnMovieListener listener) {
        super(itemView);

        this.listener = listener;
        imageView_pop = itemView.findViewById(R.id.movie_img_popular);
        ratingBar_pop = itemView.findViewById(R.id.rating_bar_pop);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
