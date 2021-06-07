package com.example.hito2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class animacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_animacion);

        //animaciones
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento1);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento2);

        TextView deTextview = findViewById(R.id.textViewde);
        TextView wlText = findViewById(R.id.textViewWL);
        ImageView logo = findViewById(R.id.imageViewW);

        deTextview.setAnimation(animacion1);
        wlText.setAnimation(animacion1);
        logo.setAnimation(animacion2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(animacion.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        },4000);
    }
}