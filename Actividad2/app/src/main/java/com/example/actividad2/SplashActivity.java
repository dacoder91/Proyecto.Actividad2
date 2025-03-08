package com.example.actividad2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    //Pantalla de Bienvenida a la App
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Usar un Handler para retrasar la transición a la siguiente actividad
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Iniciar la actividad principal
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                // Cerrar esta actividad para que el usuario no pueda volver atrás
                finish();
            }
        }, 5000); // 5000 milisegundos = 5 segundos
    }
}