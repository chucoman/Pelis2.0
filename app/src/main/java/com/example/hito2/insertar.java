package com.example.hito2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hito2.entidades.ConexionSqliteHelper;
import com.example.hito2.utilidades.utilidades;

public class insertar extends AppCompatActivity {
    Spinner comGeneros;
    Spinner comYear;
    EditText campoId, campoNombre, campoDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);
        String campoGen;
        String campoYear;

        campoId= findViewById(R.id.campoId);
        campoNombre= findViewById(R.id.campoNombre);
        comGeneros= findViewById(R.id.spinnerGenero);
        comYear= findViewById(R.id.spinnerYear);
        campoDesc= findViewById(R.id.campoDescrip);

        //rellenar spinner generos
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.combo_generos,
                android.R.layout.simple_spinner_item);
        comGeneros.setAdapter(adapter);
        comGeneros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(parent.getContext(),"Seleccionado: "+parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //rellenar spinner año
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.combo_year,
                android.R.layout.simple_spinner_item);
        comYear.setAdapter(adapter1);
        comYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),"Seleccionado: "+parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
            Toast.makeText(this, "Idiomas", Toast.LENGTH_SHORT).show();
        }else if(id ==R.id.item3) {

            Toast.makeText(this, "Registrar", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    public void onClick (View view){
        registrarPelicula();
    }
    private void registrarPelicula(){
        ConexionSqliteHelper conn=new ConexionSqliteHelper(this, "bd_pelicula", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(utilidades.CAMPO_ID,campoId.getText().toString());
        values.put(utilidades.CAMPO_NOMBRE,campoNombre.getText().toString());
        values.put(utilidades.CAMPO_GENERO,comGeneros.getSelectedItem().toString());
        values.put(utilidades.CAMPO_YEAR,comYear.getSelectedItem().toString());
        values.put(utilidades.CAMPO_DESCRIPCION,campoDesc.getText().toString());

        Long idResultante=db.insert(utilidades.TABLA_PELICULA,utilidades.CAMPO_ID,values);

        Toast.makeText(getApplicationContext(),"Id Registro:"+idResultante,Toast.LENGTH_SHORT).show();

    }
}