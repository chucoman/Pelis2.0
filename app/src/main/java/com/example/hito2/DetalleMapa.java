package com.example.hito2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetalleMapa extends AppCompatActivity {
        TextView markertxt, numertxt;
        Button Bcall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_mapa);

        markertxt = findViewById(R.id.marker);
        numertxt = findViewById(R.id.numero);

        String title = getIntent().getStringExtra("title");
        String num= getIntent().getStringExtra("numero");
        markertxt.setText(title);
        numertxt .setText(num);
        Bcall = findViewById(R.id.bcall);
        Bcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+num));
                startActivity(i);
            }
        });


    }

}