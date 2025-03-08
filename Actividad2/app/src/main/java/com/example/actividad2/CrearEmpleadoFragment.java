package com.example.actividad2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CrearEmpleadoFragment extends Fragment {

    private EditText editNombre, editPrimerApellido, editSegundoApellido, editDni, editFechaNacimiento, editDireccion;
    private EditText editPuestoNombre, editPuestoDescripcion, editHorarioEntrada, editHorarioSalida, editDiasLaborales;
    private Button btnGuardar;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_empleado, container, false);

        // Referencias a los elementos del layout
        editNombre = view.findViewById(R.id.editNombre);
        editPrimerApellido = view.findViewById(R.id.editPrimerApellido);
        editSegundoApellido = view.findViewById(R.id.editSegundoApellido);
        editDni = view.findViewById(R.id.editDni);
        editFechaNacimiento = view.findViewById(R.id.editFechaNacimiento);
        editDireccion = view.findViewById(R.id.editDireccion);
        editPuestoNombre = view.findViewById(R.id.editPuestoNombre);
        editPuestoDescripcion = view.findViewById(R.id.editPuestoDescripcion);
        editHorarioEntrada = view.findViewById(R.id.editHorarioEntrada);
        editHorarioSalida = view.findViewById(R.id.editHorarioSalida);
        editDiasLaborales = view.findViewById(R.id.editDiasLaborales);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        dbHelper = new DatabaseHelper(getContext());

        // Acción del botón "Guardar"
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarDatos()) {
                    try {
                        // Parsear fecha
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        Date fechaNacimiento = new Date(sdf.parse(editFechaNacimiento.getText().toString()).getTime());

                        // Parsear hora
                        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                        Time horarioEntrada = new Time(sdfHora.parse(editHorarioEntrada.getText().toString()).getTime());
                        Time horarioSalida = new Time(sdfHora.parse(editHorarioSalida.getText().toString()).getTime());

                        // Crear el puesto de trabajo
                        PuestoDeTrabajo puesto = new PuestoDeTrabajo(
                                0, // ID se genera automáticamente
                                editPuestoNombre.getText().toString(),
                                editPuestoDescripcion.getText().toString()
                        );

                        // Crear el horario laboral
                        HorarioLaboral horario = new HorarioLaboral(
                                0, // ID se genera automáticamente
                                horarioEntrada,
                                horarioSalida,
                                Integer.parseInt(editDiasLaborales.getText().toString())
                        );

                        // Crear el empleado
                        Empleados empleado = new Empleados(
                                0, // ID se genera automáticamente
                                editNombre.getText().toString(),
                                editPrimerApellido.getText().toString(),
                                editSegundoApellido.getText().toString(),
                                editDni.getText().toString(),
                                fechaNacimiento,
                                editDireccion.getText().toString(),
                                puesto,
                                horario
                        );

                        // Insertar el empleado junto con su puesto y horario
                        long id = dbHelper.insertEmpleadoCompleto(empleado, puesto, horario);
                        if (id != -1) {
                            Toast.makeText(getContext(), "Empleado creado con ID: " + id, Toast.LENGTH_SHORT).show();
                            limpiarFormulario();
                        } else {
                            Toast.makeText(getContext(), "Error al crear empleado", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        Toast.makeText(getContext(), "Formato de fecha u hora inválido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    // Validar datos antes de guardar
    private boolean validarDatos() {
        // Validar campos vacíos
        if (!FormularioHelper.validarDatos(editNombre, editPrimerApellido, editSegundoApellido, editDni, editFechaNacimiento, editDireccion, editPuestoNombre, editPuestoDescripcion, editHorarioEntrada, editHorarioSalida, editDiasLaborales)) {
            FormularioHelper.mostrarErrorCamposVacios(this);
            return false;
        }

        // Validar fecha
        if (!FormularioHelper.validarFecha(editFechaNacimiento.getText().toString())) {
            Toast.makeText(getContext(), "Formato de fecha inválido (yyyy-MM-dd)", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar hora
        if (!FormularioHelper.validarHora(editHorarioEntrada.getText().toString()) ||
                !FormularioHelper.validarHora(editHorarioSalida.getText().toString())) {
            Toast.makeText(getContext(), "Formato de hora inválido (HH:mm:ss)", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar días laborales
        try {
            int diasLaborales = Integer.parseInt(editDiasLaborales.getText().toString());
            if (diasLaborales < 1 || diasLaborales > 7) {
                Toast.makeText(getContext(), "Días laborales deben ser entre 1 y 7", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Días laborales deben ser un número válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Limpiar el formulario después de guardar
    private void limpiarFormulario() {
        FormularioHelper.limpiarFormulario(editNombre, editPrimerApellido, editSegundoApellido, editDni, editFechaNacimiento, editDireccion, editPuestoNombre, editPuestoDescripcion, editHorarioEntrada, editHorarioSalida, editDiasLaborales);
    }
}