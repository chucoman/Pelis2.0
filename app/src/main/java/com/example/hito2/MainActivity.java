package com.example.hito2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hito2.entidades.ConexionSqliteHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConexionSqliteHelper conn=new ConexionSqliteHelper(this, "bd_pelicula", null,1);
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
            Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show();
        }else if(id ==R.id.item2) {
            Toast.makeText(this, "Idiomas", Toast.LENGTH_SHORT).show();
        }else if(id ==R.id.item3) {
            Intent registro = new Intent(this, insertar.class);
            startActivity(registro);
        Toast.makeText(this, "Registrar", Toast.LENGTH_SHORT).show();
    }
        return super.onOptionsItemSelected(item);
    }


}