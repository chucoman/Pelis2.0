package com.example.hito2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hito2.entidades.ConexionSqliteHelper;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
            Toast.makeText(this, "Mapa", Toast.LENGTH_SHORT).show();
        }
        else if(id ==R.id.item6) {//menu de mapa
            finish();
            Toast.makeText(this, "Mapa", Toast.LENGTH_SHORT).show();}



        return super.onOptionsItemSelected(item);
    }


}