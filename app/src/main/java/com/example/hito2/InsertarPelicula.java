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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hito2.entidades.ConexionSqliteHelper;
import com.example.hito2.utilidades.utilidades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class InsertarPelicula extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT=100;
    private ImageButton btnHablar;
    Spinner comGeneros;
    Spinner comYear;
    EditText campoNombre, campoDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);
        String campoGen;
        String campoYear;

        //campoId= findViewById(R.id.campoId); al ser el campo un autoincremental no es necesario
        campoNombre= findViewById(R.id.campoNombre);
        comGeneros= findViewById(R.id.spinnerGenero);
        comYear= findViewById(R.id.spinnerYear);
        campoDesc= findViewById(R.id.campoDescrip);
        btnHablar=findViewById(R.id.btnMicro);

        btnHablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarEntradaVoz();
            }
        });

        //rellenar spinner generos
       ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.combo_generos,
               android.R.layout.simple_spinner_item);



        comGeneros.setAdapter(adapter);
        comGeneros.setPrompt(getString(R.string.Generos));
        comGeneros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(parent.getContext(),"Seleccionado: "+parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });comGeneros.setPrompt(getString(R.string.Generos));

        //rellenar spinner año
       /* ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.combo_year,
                android.R.layout.simple_spinner_item);*/ //este era el metodo antiguo metia los años de manera "manual" desde un array creado a mano en values

        //cargo el array con los años desde 1900 hasta el año almacenado en el calendario
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i)); }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        Spinner spinYear = (Spinner)findViewById(R.id.spinnerYear); spinYear.setAdapter(adapter1);



        comYear.setAdapter(adapter1);
        comYear.setPrompt(getString(R.string.Año));
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



    public void onClick (View view){
        registrarPelicula();
    }
    private void registrarPelicula(){
        ConexionSqliteHelper conn=new ConexionSqliteHelper(this, "bd_pelicula", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values= new ContentValues();
       // values.put(utilidades.CAMPO_ID,campoId.getText().toString());al ser el campo un autoincremental no es necesario
        values.put(utilidades.CAMPO_NOMBRE,campoNombre.getText().toString());
        values.put(utilidades.CAMPO_GENERO,comGeneros.getSelectedItem().toString());
        values.put(utilidades.CAMPO_YEAR,comYear.getSelectedItem().toString());
        values.put(utilidades.CAMPO_DESCRIPCION,campoDesc.getText().toString());
        Long idResultante=db.insert(utilidades.TABLA_PELICULA,utilidades.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro:"+idResultante,Toast.LENGTH_SHORT).show();

        //Volvemos a poner en blanco los campos
        campoNombre.setText("");
        campoDesc.setText("");



    }

}