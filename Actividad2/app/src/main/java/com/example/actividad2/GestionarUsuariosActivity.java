// GestionarUsuariosActivity.java
package com.example.actividad2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class GestionarUsuariosActivity extends AppCompatActivity {

    private LinearLayout buttonContainer; // Contenedor de los botones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_usuarios);

        // Referencias a los elementos del layout
        buttonContainer = findViewById(R.id.buttonContainer);
        Button btnAtras = findViewById(R.id.btnAtras);
        Button btnCrear = findViewById(R.id.btnCrear);
        Button btnModificar = findViewById(R.id.btnModificar);
        Button btnEliminar = findViewById(R.id.btnEliminar);

        // Botón "Atrás" para regresar a MainActivity
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar a MainActivity
                Intent intent = new Intent(GestionarUsuariosActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cerrar esta actividad
            }
        });

        // Botón "Crear Empleado"
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFragment(new CrearEmpleadoFragment());
            }
        });

        // Botón "Modificar Empleado"
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFragment(new ModificarEmpleadoFragment());
            }
        });

        // Botón "Eliminar Empleado"
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFragment(new EliminarEmpleadoFragment());
            }
        });
    }

    // Método para cargar un fragment en el contenedor
    private void cargarFragment(Fragment fragment) {
        // Ocultar los botones
        buttonContainer.setVisibility(View.GONE);

        // Cargar el fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null); // Permitir volver atrás
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        // Mostrar los botones al volver atrás
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            buttonContainer.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}