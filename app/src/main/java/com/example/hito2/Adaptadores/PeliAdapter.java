package com.example.hito2.Adaptadores;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hito2.MainActivity;
import com.example.hito2.Modif;
import com.example.hito2.R;
import com.example.hito2.entidades.ConexionSqliteHelper;
import com.example.hito2.entidades.Pelicula;

import java.util.List;

public class PeliAdapter extends RecyclerView.Adapter<PeliAdapter.ViewHolder>{
    private List<Pelicula> listaPelicula;
    private Context mContext;
    private RecyclerView mReciclerV;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView id;
        public TextView nombre;
        public TextView genero;
        public TextView year;
        public TextView descripcion;
        public View layout;


        public ViewHolder(View v){
            super(v);
            layout = v;
            id = (TextView) v.findViewById(R.id.textId);
            nombre = (TextView) v.findViewById(R.id.textNomb);
            genero = (TextView) v.findViewById(R.id.textGen);
            year = (TextView) v.findViewById(R.id.textYear);
            descripcion = (TextView) v.findViewById(R.id.textDesc);


        }
    }

    public void add(int position, Pelicula pelicula ){
        listaPelicula.add(position, pelicula);
        notifyItemInserted(position);
    }
    public void remove(int position){
        listaPelicula.remove(position);
        notifyItemRemoved(position);
    }




    public PeliAdapter(List<Pelicula> dataset, Context context, RecyclerView recyclerView){
        listaPelicula = dataset;
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
        final Pelicula pelicula = listaPelicula.get(position);
        holder.id.setText(pelicula.getId().toString());
        holder.nombre.setText(pelicula.getNombre());
        holder.genero.setText(pelicula.getGenero());
        holder.year.setText(pelicula.getYear().toString());
        holder.descripcion.setText(pelicula.getDescripcion());

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
                        String i = pelicula.getId().toString();

                        Toast.makeText(mContext, i, Toast.LENGTH_SHORT).show();
                        llevarAModificar(pelicula.getId());

                    }
                }).setNeutralButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConexionSqliteHelper dbHelper = new ConexionSqliteHelper(mContext);
                        dbHelper.borrarPelicula(pelicula.getId(), mContext);
                        listaPelicula.remove(position);
                        mReciclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listaPelicula.size());
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

        return listaPelicula.size();
    }



    public void deleteItem(int index) {
        listaPelicula.remove(index);
        notifyItemRemoved(index);
    }
    private void llevarAModificar(long peliId){

        Intent modif = new Intent(mContext, Modif.class);
        modif.putExtra("Peli_id", peliId);
        mContext.startActivity(modif);
    }


    public void RemoveItem(int position){
    listaPelicula.remove(position);
    notifyDataSetChanged();
    }
}
