package com.example.hito2.Adaptadores;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hito2.R;

import org.w3c.dom.Text;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {




        ImageView imageView;
        RatingBar ratingBar;

    //escuchador del click
    OnMovieListener onMovieListener;

    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);
        this.onMovieListener = onMovieListener;


        imageView = itemView.findViewById(R.id.movie_img);
        ratingBar = itemView.findViewById(R.id.rating_bar);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {onMovieListener.onMovieClick(getAdapterPosition());}
}
