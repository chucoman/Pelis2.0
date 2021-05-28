package com.example.hito2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hito2.Adaptadores.PeliAdapter;
import com.example.hito2.Conexion.ConexionSqliteHelper;
import com.example.hito2.Retrofit.SearchResponse;


public class ListaPelis extends AppCompatActivity {
private RecyclerView mRecyclerView;
private RecyclerView.LayoutManager mLayaoutManager;
private ConexionSqliteHelper dbHelper;
private PeliAdapter adapter;
String filtro ="";

ConexionSqliteHelper conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pelis);

        mRecyclerView=(RecyclerView)findViewById(R.id.my_recycler_pelis);
        mRecyclerView.setHasFixedSize(true);

        mLayaoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayaoutManager);

        precyclerView(filtro);

    }
    private void precyclerView(String filtro){
        dbHelper = new ConexionSqliteHelper(this);
        adapter = new PeliAdapter(dbHelper.pelisList(filtro),this,mRecyclerView);
        mRecyclerView.setAdapter(adapter);

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
            Intent mapa = new Intent(this, Map.class);
            startActivity(mapa);
            Toast.makeText(this, "Mapa", Toast.LENGTH_SHORT).show();
        }
        else if(id ==R.id.item6) {//Salir
            Intent pelis = new Intent(this, SearchResponse.class);
            startActivity(pelis);
            Toast.makeText(this, "Cerrando Aplicacion", Toast.LENGTH_SHORT).show();}



        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}