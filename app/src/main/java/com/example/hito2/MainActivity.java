package com.example.hito2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mfirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    public static final int REQUEST_CODE = 4859; //constante

    List<AuthUI.IdpConfig> provider = Arrays.asList(
            // new AuthUI.IdpConfig.FacebookBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build()
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Hito2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mfirebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user !=null){

                    Toast.makeText(MainActivity.this,"Iniciaste sesion", Toast.LENGTH_SHORT).show();

                }else{
                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(provider)
                            .setIsSmartLockEnabled(false).build(), REQUEST_CODE
                    );
                }
            }
        };


    }
    ////metodos de autenticacion
    @Override
    protected void onResume() {
        super.onResume();
        mfirebaseAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mfirebaseAuth.removeAuthStateListener(mAuthListener);
    }
    /*public void cerarSesion(View view){
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this,"Sesion cerrada", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }*/

    //funciones botones generos
    public void Ficcion(View view){
        Intent listfic= new Intent(this, ListGeneros.class);
        listfic.putExtra("Genero","Ficcion");
        startActivity(listfic);
    }
    public void Accion(View view){
        Intent listac = new Intent(this, ListGeneros.class);
        listac.putExtra("Genero","Accion");
        startActivity(listac);

    }
    public void Amor(View view){
        Intent listam = new Intent(this, ListGeneros.class);
        listam.putExtra("Genero","Amor");
        startActivity(listam);
    }
    public void Drama(View view){
        Intent listdr= new Intent(this, ListGeneros.class);
        listdr.putExtra("Genero","Drama");
        startActivity(listdr);
    }
    public void Aventura(View view){
        Intent listav = new Intent(this, ListGeneros.class);
        listav.putExtra("Genero","Aventuras");
        startActivity(listav);

    }
    public void Terror(View view){
        Intent listte = new Intent(this, ListGeneros.class);
        listte.putExtra("Genero","Terror");
        startActivity(listte);

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
            Intent peli = new Intent(this, PelisJson.class);
            startActivity(peli);
            Toast.makeText(this, "Cerrando Aplicacion", Toast.LENGTH_SHORT).show();}



        return super.onOptionsItemSelected(item);
    }


}