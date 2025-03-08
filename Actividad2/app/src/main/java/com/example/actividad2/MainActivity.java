package com.example.actividad2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnGestionarUsuarios = findViewById(R.id.btnGestionarUsuarios);
        Button btnVerUsuarios = findViewById(R.id.btnVerUsuarios);


        btnGestionarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir GestionarUsuariosActivity sin parámetros
                Intent intent = new Intent(MainActivity.this, GestionarUsuariosActivity.class);
                startActivity(intent);
            }
        });

        btnVerUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir VerUsuariosActivity
                Intent intent = new Intent(MainActivity.this, VerUsuariosActivity.class);
                startActivity(intent);
            }
        });


        };

}
