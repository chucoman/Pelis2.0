package com.example.hito2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hito2.Adaptadores.ListaPeliculasadapter;
import com.example.hito2.entidades.ConexionSqliteHelper;
import com.example.hito2.entidades.Pelicula;
import com.example.hito2.utilidades.utilidades;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ListGeneros extends AppCompatActivity {
    ArrayList<Pelicula> listPelicula;
    RecyclerView recyclerViewPeliculas;
    private SwipeRefreshLayout swipeRefreshLayout;
    ConexionSqliteHelper conn;
    ListaPeliculasadapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_generos);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        conn = new ConexionSqliteHelper(getApplicationContext(),"bd_pelicula", null,1);

        listPelicula=new ArrayList<>();
        recyclerViewPeliculas= (RecyclerView) findViewById(R.id.my_recycler_pelis);
        recyclerViewPeliculas.setLayoutManager(new LinearLayoutManager(this));

        consultarListaPeliculas();

        adapter=new ListaPeliculasadapter(listPelicula);
        recyclerViewPeliculas.setAdapter(adapter);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Esto se ejecuta cada vez que se realiza el gesto
                listPelicula.clear();
                consultarListaPeliculas();

                ListaPeliculasadapter adapter=new ListaPeliculasadapter(listPelicula);
                recyclerViewPeliculas.setAdapter(adapter);


                swipeRefreshLayout.setRefreshing(false);
            }
        });


        //refresh al deslizar para abajo
        adapter.serOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Seleccionada : "+ listPelicula.get(recyclerViewPeliculas.getChildAdapterPosition(v)).getNombre(),
                        Toast.LENGTH_SHORT).show();


                int id = listPelicula.get(recyclerViewPeliculas.getChildAdapterPosition(v)).getId();
                BoradoPeli(id);

                listPelicula.clear();
                consultarListaPeliculas();
                recyclerViewPeliculas.setAdapter(adapter);



            }

        });





    }

    private void consultarListaPeliculas(){
        String genero = getIntent().getStringExtra("Genero");
        SQLiteDatabase db=conn.getReadableDatabase();

        Pelicula pelicula = null;

        Cursor cursor= db.rawQuery("SELECT * FROM " + utilidades.TABLA_PELICULA +" WHERE "+ utilidades.CAMPO_GENERO +" = "+ "'"+ genero +"'", null);

        while(cursor.moveToNext()){

            pelicula=new Pelicula();

            pelicula.setId(cursor.getInt(0));
            pelicula.setNombre(cursor.getString(1));
            pelicula.setGenero(cursor.getString(2));
            pelicula.setYear(cursor.getInt(3));
            pelicula.setDescripcion(cursor.getString(4));

            listPelicula.add(pelicula);
            db.close();

        }
    }
    //Mostrar y ocultar el menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;

    }




    //fuciones botones menu
    public boolean onOptionsItemSelected(MenuItem item){
        int id =item.getItemId();
        if(id ==R.id.item1) {
            Intent inicio = new Intent(this, MainActivity.class);
            startActivity(inicio);
            Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show();

        }else if(id ==R.id.item2) {
            Intent idiomas = new Intent(this, InfoPopUp.class);
            startActivity(idiomas);
            Toast.makeText(this, "Idiomas", Toast.LENGTH_SHORT).show();
        }else if(id ==R.id.item3) {
            Intent registro = new Intent(this, InsertarPelicula.class);
            startActivity(registro);
            Toast.makeText(this, "Registrar", Toast.LENGTH_SHORT).show();
        }
        else if(id ==R.id.item4) {
            Intent listar = new Intent(this, ListaPelis.class);
            startActivity(listar);
            Toast.makeText(this, "Listar", Toast.LENGTH_SHORT).show();

        }
        else if(id ==R.id.item5) {//menu de mapa
            Intent listar = new Intent(this, Map.class);
            startActivity(listar);
            Toast.makeText(this, "Mapa", Toast.LENGTH_SHORT).show();}



        return super.onOptionsItemSelected(item);
    }
    public void BoradoPeli(int id){
        SQLiteDatabase db=conn.getReadableDatabase();
        db.execSQL("DELETE FROM " + utilidades.TABLA_PELICULA +" WHERE ID =" + id);
        db.close();


        }






}