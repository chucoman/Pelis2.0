package com.example.hito2.Adaptadores;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hito2.ListaPelis;
import com.example.hito2.R;
import com.example.hito2.entidades.Pelicula;

import java.util.ArrayList;

public class ListaPeliculasadapter
        extends RecyclerView.Adapter<ListaPeliculasadapter.ViewHolderDatos>
        implements View.OnClickListener{

    ArrayList<Pelicula> listaPelicula;
    private View.OnClickListener listener;



    public ListaPeliculasadapter(ArrayList<Pelicula> listaPelicula){
        this.listaPelicula = listaPelicula;
    }
    public void deleteItem(int index) {
        listaPelicula.remove(index);
        notifyItemRemoved(index);
    }


    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list,null,false);

        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.id.setText(listaPelicula.get(position).getId().toString());
        holder.nombre.setText(listaPelicula.get(position).getNombre().toString());
        holder.genero.setText(listaPelicula.get(position).getGenero().toString());
        holder.year.setText(listaPelicula.get(position).getYear().toString());
        holder.descripcion.setText(listaPelicula.get(position).getDescripcion().toString());



    }

    @Override
    public int getItemCount() {
        return listaPelicula.size();
    }

    public void serOnClickListener(View.OnClickListener listener ){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }

    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView id, nombre, genero, year, descripcion;
        RecyclerView recycler;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.textId);
            nombre = (TextView) itemView.findViewById(R.id.textNomb);
            genero = (TextView) itemView.findViewById(R.id.textGen);
            year = (TextView) itemView.findViewById(R.id.textYear);
            descripcion = (TextView) itemView.findViewById(R.id.textDesc);
            recycler=itemView.findViewById(R.id.my_recycler_pelis);


        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Selecciona Una opcion");
            menu.add(getAdapterPosition(),101,0,"Modificar");
            menu.add(getAdapterPosition(),102,1,"Borrar");

        }
    }
    public void RemoveItem(int position){
    listaPelicula.remove(position);
    notifyDataSetChanged();
    }
}
