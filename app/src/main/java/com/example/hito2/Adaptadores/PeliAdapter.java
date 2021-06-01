package com.example.hito2.Adaptadores;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hito2.Modif;
import com.example.hito2.R;
import com.example.hito2.Conexion.ConexionSqliteHelper;
import com.example.hito2.Conexion.MovieSqlite;

import java.util.List;

public class PeliAdapter extends RecyclerView.Adapter<PeliAdapter.ViewHolder>{
    private List<MovieSqlite> listaMovieSqlite;
    private Context mContext;
    private RecyclerView mReciclerV;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView id;
        public TextView nombre;
        public TextView genero;
        public TextView year;
        public de.hdodenhof.circleimageview.CircleImageView imagen;
        public TextView descripcion;
        public View layout;


        public ViewHolder(View v){
            super(v);
            layout = v;
            id = (TextView) v.findViewById(R.id.textId);
            nombre = (TextView) v.findViewById(R.id.textNomb);
            genero = (TextView) v.findViewById(R.id.textGen);
            year = (TextView) v.findViewById(R.id.textYear);
            imagen =(de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.profileIv1);
            descripcion = (TextView) v.findViewById(R.id.textDesc);


        }
    }

    public void add(int position, MovieSqlite movieSqlite){
        listaMovieSqlite.add(position, movieSqlite);
        notifyItemInserted(position);
    }
    public void remove(int position){
        listaMovieSqlite.remove(position);
        notifyItemRemoved(position);
    }




    public PeliAdapter(List<MovieSqlite> dataset, Context context, RecyclerView recyclerView){
        listaMovieSqlite = dataset;
        mContext = context;
        mReciclerV = recyclerView;

    }
    @Override
    public PeliAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //crear nuevo view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final MovieSqlite movieSqlite = listaMovieSqlite.get(position);
        holder.id.setText(movieSqlite.getId().toString());
        holder.nombre.setText(movieSqlite.getNombre());
        holder.genero.setText(movieSqlite.getGenero());
        holder.year.setText(movieSqlite.getYear().toString());
        holder.descripcion.setText(movieSqlite.getDescripcion().toString());

        if (movieSqlite.getImagen().equals("null")){
            // no hay imagen en el registro, establecer predeterminado
            holder.imagen.setImageResource(R.drawable.ic_photo_black);
        }
        else {
            // tener imagen en el registro
           // holder.imagen.setImageURI(Uri.parse(movieSqlite.getImagen()));
            Glide.with(mContext)
                    .load(movieSqlite.getImagen())
                    .into(holder.imagen);
        }


        //escuchador de un click
        holder.layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Elige una opcion");
                builder.setMessage("Modificar o Borrar");
                builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ir a modificar
                        String i = movieSqlite.getId().toString();

                        Toast.makeText(mContext, i, Toast.LENGTH_SHORT).show();
                        llevarAModificar(movieSqlite.getId());

                    }
                }).setNeutralButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConexionSqliteHelper dbHelper = new ConexionSqliteHelper(mContext);
                        dbHelper.borrarPelicula(movieSqlite.getId(), mContext);
                        listaMovieSqlite.remove(position);
                        mReciclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listaMovieSqlite.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return listaMovieSqlite.size();
    }



    public void deleteItem(int index) {
        listaMovieSqlite.remove(index);
        notifyItemRemoved(index);
    }
    private void llevarAModificar(long peliId){

        Intent modif = new Intent(mContext, Modif.class);
        modif.putExtra("Peli_id", peliId);
        mContext.startActivity(modif);
    }


    public void RemoveItem(int position){
    listaMovieSqlite.remove(position);
    notifyDataSetChanged();
    }
}
