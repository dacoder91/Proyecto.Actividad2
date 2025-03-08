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

public class ModificarEmpleadoFragment extends Fragment {

    private EditText editId, editNombre, editPrimerApellido, editSegundoApellido, editDni, editFechaNacimiento, editDireccion;
    private EditText editPuestoNombre, editPuestoDescripcion, editHorarioEntrada, editHorarioSalida, editDiasLaborales;
    private Button btnBuscar, btnGuardar;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar_empleado, container, false);

        // Referencias a los elementos del layout
        editId = view.findViewById(R.id.editId);
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
        btnBuscar = view.findViewById(R.id.btnBuscar);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        dbHelper = new DatabaseHelper(getContext());

        // Acción del botón "Buscar"
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int id = Integer.parseInt(editId.getText().toString());
                    Empleados empleado = dbHelper.getEmpleado(id);
                    if (empleado != null) {
                        // Mostrar los datos del empleado
                        editNombre.setText(empleado.getNombre());
                        editPrimerApellido.setText(empleado.getPrimerApellido());
                        editSegundoApellido.setText(empleado.getSegundoApellido());
                        editDni.setText(empleado.getDni());

                        // Formatear la fecha al nuevo formato dd-MM-yyyy
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        editFechaNacimiento.setText(sdf.format(empleado.getFechaNacimiento()));

                        editDireccion.setText(empleado.getDireccion());

                        // Mostrar los datos del puesto de trabajo
                        PuestoDeTrabajo puesto = empleado.getPuestoDeTrabajo();
                        editPuestoNombre.setText(puesto.getNombre());
                        editPuestoDescripcion.setText(puesto.getDescripcion());

                        // Mostrar los datos del horario laboral
                        HorarioLaboral horario = empleado.getHorarioLaboral();
                        editHorarioEntrada.setText(horario.getHorarioEntrada().toString());
                        editHorarioSalida.setText(horario.getHorarioSalida().toString());
                        editDiasLaborales.setText(String.valueOf(horario.getDiasLaborales()));
                    } else {
                        Toast.makeText(getContext(), "Empleado no encontrado", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "ID inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Acción del botón "Guardar"
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarDatos()) {
                    try {
                        // Obtener el empleado existente
                        int id = Integer.parseInt(editId.getText().toString());
                        Empleados empleado = dbHelper.getEmpleado(id);

                        // Actualizar los datos del empleado
                        empleado.setNombre(editNombre.getText().toString());
                        empleado.setPrimerApellido(editPrimerApellido.getText().toString());
                        empleado.setSegundoApellido(editSegundoApellido.getText().toString());
                        empleado.setDni(editDni.getText().toString());

                        // Parsear la fecha con el nuevo formato dd-MM-yyyy
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        Date fechaNacimiento = new Date(sdf.parse(editFechaNacimiento.getText().toString()).getTime());
                        empleado.setFechaNacimiento(fechaNacimiento);

                        empleado.setDireccion(editDireccion.getText().toString());

                        // Actualizar el puesto de trabajo
                        PuestoDeTrabajo puesto = empleado.getPuestoDeTrabajo();
                        puesto.setNombre(editPuestoNombre.getText().toString());
                        puesto.setDescripcion(editPuestoDescripcion.getText().toString());

                        // Actualizar el horario laboral
                        HorarioLaboral horario = empleado.getHorarioLaboral();
                        horario.setHorarioEntrada(Time.valueOf(editHorarioEntrada.getText().toString()));
                        horario.setHorarioSalida(Time.valueOf(editHorarioSalida.getText().toString()));
                        horario.setDiasLaborales(Integer.parseInt(editDiasLaborales.getText().toString()));

                        // Actualizar en la base de datos
                        dbHelper.updateEmpleado(empleado);
                        dbHelper.updatePuesto(puesto);
                        dbHelper.updateHorario(horario);

                        Toast.makeText(getContext(), "Empleado actualizado", Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        Toast.makeText(getContext(), "Formato de fecha inválido (dd-MM-yyyy)", Toast.LENGTH_SHORT).show();
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(getContext(), "Formato de hora inválido (HH:mm:ss)", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Formato de fecha inválido (dd-MM-yyyy)", Toast.LENGTH_SHORT).show();
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
}