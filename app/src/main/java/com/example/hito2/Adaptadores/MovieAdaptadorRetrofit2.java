package com.example.hito2.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hito2.R;
import com.example.hito2.Retrofit.models.MovieModel;
import com.example.hito2.Retrofit.utilidades.Credenciales;

import java.util.List;

public class MovieAdaptadorRetrofit2 extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;
    private static final int DISPLAY_POP = 1;
    private static final int DISPLAY_SEARCH = 2;


    public MovieAdaptadorRetrofit2 (OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list,
                parent,false);
        return new MovieViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {


        int itemViewType = getItemViewType(i);
        if (itemViewType == DISPLAY_SEARCH){

            // vote average is over 10, and our rating bar is over 5 stars: dividing by 2
            ((MovieViewHolder)holder).ratingBar.setRating((mMovies.get(i).getVote_average())/2);

            // ImageView: Using Glide Library
            Glide.with(holder.itemView.getContext())
                    .load( "https://image.tmdb.org/t/p/w500/"
                            +mMovies.get(i).getPoster_path())
                    .into(((MovieViewHolder)holder).imageView);

        }else{
            ((MovieViewHolderPopular)holder).ratingBar_pop.setRating(mMovies.get(i).getVote_average());

            // ImageView: Using Glide Library
            Glide.with(holder.itemView.getContext())
                    .load( "https://image.tmdb.org/t/p/w500/"
                            +mMovies.get(i).getPoster_path())
                    .into(((MovieViewHolderPopular)holder).imageView_pop);

        }


    }
    @Override
    public int getItemCount() {
        if (mMovies != null){
            return mMovies.size();
        }
        return 0;
    }


    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
            }


    //get id de la pelicula selecionada
    public MovieModel getSelectedMovie(int posicion){
        if (mMovies != null){
            if (mMovies.size() > 0){
                return mMovies.get(posicion);
            }
        }
        return  null;
    }
    @Override
    public int getItemViewType(int position) {

        if (Credenciales.POPULAR){
            return DISPLAY_POP;
        }
        else
            return DISPLAY_SEARCH;
    }


}
