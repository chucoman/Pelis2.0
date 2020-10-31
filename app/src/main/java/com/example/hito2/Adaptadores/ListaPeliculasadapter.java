package com.example.hito2.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hito2.R;
import com.example.hito2.entidades.Pelicula;

import java.util.ArrayList;

public class ListaPeliculasadapter extends RecyclerView.Adapter<ListaPeliculasadapter.ViewHolderDatos> {

    ArrayList<Pelicula> listaPelicula;

    public ListaPeliculasadapter(ArrayList<Pelicula> listaPelicula){
        this.listaPelicula = listaPelicula;
    }


    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list,null,false);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.id.setText(listaPelicula.get(position).getId().toString());
        holder.nombre.setText(listaPelicula.get(position).getNombre().toString());
        holder.genero.setText(listaPelicula.get(position).getGenero().toString());
        holder.year.setText(listaPelicula.get(position).getYear().toString());



    }

    @Override
    public int getItemCount() {
        return listaPelicula.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView id, nombre, genero, year;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.textId);
            nombre = (TextView) itemView.findViewById(R.id.textNomb);
            genero = (TextView) itemView.findViewById(R.id.textGen);
            year = (TextView) itemView.findViewById(R.id.textYear);

        }
    }
}
