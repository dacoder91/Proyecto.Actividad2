// FormularioHelper.java
package com.example.actividad2;

import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

//Funciones auxiliares para el manejo de formularios
public class FormularioHelper {

    // Método para validar que todos los campos estén completos
    public static boolean validarDatos(EditText... campos) {
        for (EditText campo : campos) {
            if (campo.getText().toString().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // Método para limpiar todos los campos del formulario
    public static void limpiarFormulario(EditText... campos) {
        for (EditText campo : campos) {
            campo.setText("");
        }
    }

    // Método para mostrar un mensaje de error si algún campo está vacío
    public static void mostrarErrorCamposVacios(Fragment fragment) {
        Toast.makeText(fragment.getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
    }

    // Método para validar el formato de la fecha (yyyy-MM-dd)
    // FormularioHelper.java
    public static boolean validarFecha(String fecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            sdf.setLenient(false); // No permitir fechas inválidas como 30-02-2023
            sdf.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Método para validar el formato de la hora (HH:mm:ss)
    public static boolean validarHora(String hora) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault());
            sdf.setLenient(false);
            sdf.parse(hora);
            return true;
        } catch (java.text.ParseException e) {
            return false;
        }
    }
}