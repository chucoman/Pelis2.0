package com.example.hito2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hito2.entidades.ConexionSqliteHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConexionSqliteHelper conn=new ConexionSqliteHelper(this, "bd_pelicula", null,1);
    }

    //funciones botones generos
    public void Ficcion(View view){
        Intent list= new Intent(this, ListGeneros.class);
        list.putExtra("Genero","Ficcion");
        startActivity(list);
    }
    public void Accion(View view){
        Intent list = new Intent(this, ListGeneros.class);
        list.putExtra("Genero","Accion");
        startActivity(list);

    }
    public void Amor(View view){
        Intent list = new Intent(this, ListGeneros.class);
        list.putExtra("Genero","Amor");
        startActivity(list);
    }
    public void Drama(View view){
        Intent list= new Intent(this, ListGeneros.class);
        list.putExtra("Genero","Drama");
        startActivity(list);
    }
    public void Aventura(View view){
        Intent list = new Intent(this, ListGeneros.class);
        list.putExtra("Genero","Aventuras");
        startActivity(list);

    }
    public void Terror(View view){
        Intent list = new Intent(this, ListGeneros.class);
        list.putExtra("Genero","Terror");
        startActivity(list);

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


}