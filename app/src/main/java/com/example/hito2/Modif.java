package com.example.hito2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hito2.entidades.ConexionSqliteHelper;
import com.example.hito2.entidades.Pelicula;
import com.example.hito2.utilidades.utilidades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Modif extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT=100;
    private ImageButton btnHablar;
    private Button btnupdate;
    private ConexionSqliteHelper dbHelper;

    EditText campoNombre, campoDesc, comGeneros, comYear;
    private long recivePeliId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif);

        String campoGen;
        String campoYear;

        //campoId= findViewById(R.id.campoId); al ser el campo un autoincremental no es necesario
        campoNombre= findViewById(R.id.McampoNombre);
        comGeneros= findViewById(R.id.MspinnerGenero);
        comYear= findViewById(R.id.MspinnerYear);
        campoDesc= findViewById(R.id.McampoDescrip);
        btnHablar=findViewById(R.id.MbtnMicro);
        btnupdate=findViewById(R.id.MbtnRegistro);
        dbHelper = new ConexionSqliteHelper(this);

        try{
            recivePeliId =getIntent().getLongExtra("Peli_id",1);
        }catch(Exception e){
            e.printStackTrace();
        }
        Pelicula modiPeli=dbHelper.getPelicula(recivePeliId);
        campoNombre.setText(modiPeli.getNombre());
        campoDesc.setText(modiPeli.getDescripcion());
        comYear.setText(modiPeli.getYear().toString());
        comGeneros.setText(modiPeli.getGenero());



        btnHablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarEntradaVoz();
            }
        });



        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePelicula();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_CODE_SPEECH_INPUT: {
                if(resultCode== RESULT_OK && null!=data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    campoDesc.setText(result.get(0));

                }
                break;
            }
        }

    }

    private void iniciarEntradaVoz() {

        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()); //reconocer el idioma del movil
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hola dime la descripcion"); //frase que aparece al presionar
        try{
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        }catch (ActivityNotFoundException e){

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




     private void updatePelicula(){
        String nombre = campoNombre.getText().toString().trim();
        String genero = comGeneros.getText().toString().trim();
        Integer year = Integer.valueOf(comYear.getText().toString().trim());
        String descripcion = campoDesc.getText().toString().trim();

        if(nombre.isEmpty()){
            //error name is empty
            Toast.makeText(this, "Tienes que introducir el nombre", Toast.LENGTH_SHORT).show();
        }
        if(genero.isEmpty()){
            //error name is empty
            Toast.makeText(this, "Tienes que introducir el genero", Toast.LENGTH_SHORT).show();
        }

        Pelicula UDPelicula = new Pelicula(nombre, genero, year, descripcion);
        dbHelper.updatePelicula(recivePeliId,this,UDPelicula);
        volver();
    }
    private void volver(){
        startActivity(new Intent(this,MainActivity.class));
    }

}