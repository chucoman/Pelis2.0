package com.example.hito2;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hito2.Conexion.ConexionSqliteHelper;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class InsertarPelicula extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT=100;

    private  static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    //selección de imagen Constants
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    private Uri imageUri;
    // matrices de permisos
    private String[] cameraPermissions; // cámara y almacenamiento
    private String [] storagePermissions;// solo almacenamiento
    private ImageButton btnHablar;
    private CircleImageView profileIv;
    Spinner comGeneros;
    Spinner comYear;
    EditText campoNombre, campoDesc;
    private String nombre, genero, year,  descrip;
    private ConexionSqliteHelper dbHelper;
    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);


        //campoId= findViewById(R.id.campoId); al ser el campo un autoincremental no es necesario
        profileIv = findViewById(R.id.profileIv);
        campoNombre= findViewById(R.id.campoNombre);
        comGeneros= findViewById(R.id.spinnerGenero);
        comYear= findViewById(R.id.spinnerYear);
        campoDesc= findViewById(R.id.campoDescrip);
        btnHablar=findViewById(R.id.btnMicro);
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //inicializar bd helper
        dbHelper = new ConexionSqliteHelper(this);

        btnHablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarEntradaVoz();
            }
        });
        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // muestra el cuadro de diálogo de selección de imagen
                imagePickDialog();
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
                android.R.layout.simple_spinner_item);*/
        //este era el metodo antiguo metia los años de manera "manual" desde un array creado a mano en values

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
    private void registrarPelicula(){
        nombre = ""+campoNombre.getText().toString().trim();
        genero = ""+comGeneros.getSelectedItem().toString().trim();
        year = ""+comYear.getSelectedItem().toString().trim();
        descrip = ""+campoDesc.getText().toString().trim();

        try {
            long id = dbHelper.insertPelicula("" + nombre, "" + genero, "" + year, "" + imageUri, "" + descrip);


            Toast.makeText(getApplicationContext(), "Id Registro: registro guardado", Toast.LENGTH_SHORT).show();
            setPendingIntent();
            createNotificationChannel(); //para versiones oreo y posteriores
            createNotification();       //para versioens anteriores a oreo


            //Volvemos a poner en blanco los campos
            campoNombre.setText("");
            campoDesc.setText("");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void setPendingIntent(){
        Intent intent = new Intent(this, ListaPelis.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ListaPelis.class);
        stackBuilder.addNextIntentWithParentStack(intent);
        pendingIntent = stackBuilder.getPendingIntent(1,PendingIntent.FLAG_UPDATE_CURRENT);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //image picked from camera or gallery will be received hare
        if (resultCode == RESULT_OK){
            //Image is picked
            switch(requestCode){
                case REQ_CODE_SPEECH_INPUT: {
                    if(resultCode== RESULT_OK && null!=data){
                        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        campoDesc.setText(result.get(0));

                    }
                    break;}
                    case IMAGE_PICK_GALLERY_CODE: {
                        CropImage.activity(data.getData())
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .start(this);
                        break;

                    }
                case IMAGE_PICK_CAMERA_CODE:{
                    //Picked from camera
                    //crop Image
                    CropImage.activity(imageUri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .start(this);
                    break;
                }
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:{
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK){
                        Uri resultUri = result.getUri();
                        imageUri = resultUri;
                        //set Image
                        profileIv.setImageURI(resultUri);
                    }
                    else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                        //ERROR
                        Exception error = result.getError();
                        Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

            }


        }

        super.onActivityResult(requestCode, resultCode, data);
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
    private boolean checkStoragePermission(){
        //comprobar si el permiso de almacenamiento está habilitado o no
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }
    private  void requestStoragePermission(){
        // solicita el permiso de almacenamiento
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }
    private boolean checkCameraPermission(){
        // verifica si el permiso de la cámara está habilitado o no
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }
    private void requestCameraPermission(){
        // solicita el permiso de la cámara
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }
    private void imagePickDialog(){
        // opciones para mostrar en el diálogo
        String[] options = {"Camara", "Galeria"};
        //dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Titulo
        builder.setTitle("Seleccionar imagen");
        // establecer elementos / opciones
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // manejar clicks
                if (which==0){
                    //click en camara
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else{
                        // permiso ya otorgado
                        PickFromCamera();
                    }

                }
                else if (which==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else{
                        // permiso ya otorgado
                        PickFromGallery();
                    }
                }
            }
        });

        // Crear / mostrar diálogo
        builder.create().show();
    }
    private void PickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la Imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción de la imagen");
        //put image Uri
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        // Intento de abrir la cámara para la imagen
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }
    private void PickFromGallery() {
        // intento de elegir la imagen de la galería, la imagen se devolverá en el método onActivityResult
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // resultado del permiso permitido / denegado

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted){
                        // ambos permisos permitidos
                        PickFromCamera();
                    }
                    else{
                        Toast.makeText(this, "Se requieren permisos de cámara y almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){

                    // si se permite devolver verdadero de lo contrario falso
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        // permiso de almacenamiento permitido
                        PickFromGallery();
                    }
                    else{
                        Toast.makeText(this, "Se requiere permiso de almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }
    }


    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);


        }
    }


    private void createNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo_small);
        builder.setContentTitle("Notificacion de insercion");
        builder.setContentText("Se a insertado una nueva pelicula en la base de datos personal");
        builder.setColor(Color.WHITE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID,builder.build());
    } //metodo previo a oreo

}