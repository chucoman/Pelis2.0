package com.example.hito2;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button btn_hibrido, btn_normal, btn_satelite, btn_terreno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_hibrido = findViewById(R.id.btnHibrido);
        btn_normal = findViewById(R.id.btnNormal);
        btn_satelite = findViewById(R.id.btnSatelite);
        btn_terreno = findViewById(R.id.btnTerreno);


    }
    //Mostrar y ocultar el menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;

    }
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

    public void CHibrido(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    public void CNormal(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void CSatelite(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void CTerreno(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng ies = new LatLng(43.3843347, -3.2162576);
        mMap.addMarker(new MarkerOptions().position(ies).title("Ataulfo Argenta"));

        LatLng TLiceo = new LatLng(43.4439, -3.4576);
        mMap.addMarker(new MarkerOptions().position(TLiceo).title("Teatro Casino Liceo de Santoña"));

        LatLng CCDV = new LatLng(43.4098, -3.4161);
        mMap.addMarker(new MarkerOptions().position(CCDV).title("Casa de Cultura Doctor Velasco"));

        LatLng SB = new LatLng(43.400706, -3.8235783);
        mMap.addMarker(new MarkerOptions().position(SB).title("Sala Bretón"));

        LatLng CBDS = new LatLng(43.4647, -3.8044);
        mMap.addMarker(new MarkerOptions().position(CBDS).title("CINESA BAHIA DE SANTANDER 3D"));

        LatLng PCC = new LatLng(43.4502705, -3.8520585);
        mMap.addMarker(new MarkerOptions().position(PCC).title("Peñacastillo Cinemas 12 - Cines - UCC"));

        LatLng CA = new LatLng(43.4647, -3.8044);
        mMap.addMarker(new MarkerOptions().position(CA).title("Cine Los Ángeles"));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ies, 12));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }
}