package com.example.actividad2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VerUsuariosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EmpleadoAdapter empleadoAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_usuarios);

        // Inicializar DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Botón "Atrás"
        Button btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar a MainActivity
                Intent intent = new Intent(VerUsuariosActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cerrar esta actividad
            }
        });

        try {
            // Obtener la lista de empleados
            List<Empleados> listaEmpleados = dbHelper.getAllEmpleados();

            // Configurar el adaptador
            empleadoAdapter = new EmpleadoAdapter(listaEmpleados);
            recyclerView.setAdapter(empleadoAdapter);
        } catch (FechaInvalidaException e) {
            // Manejar el error si la fecha no está en el formato correcto
            Log.e("VerUsuariosActivity", "Error al parsear la fecha: " + e.getMessage());
            Toast.makeText(this, "Error al procesar la fecha de nacimiento de un empleado", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // Manejar cualquier otro error inesperado
            Log.e("VerUsuariosActivity", "Error al obtener la lista de empleados", e);
            Toast.makeText(this, "Error al cargar la lista de empleados", Toast.LENGTH_SHORT).show();
        }
    }
}