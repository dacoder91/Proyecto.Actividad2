package com.example.actividad2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class EliminarEmpleadoFragment extends Fragment {

    private EditText editId;
    private Button btnBuscar, btnEliminar;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eliminar_empleado, container, false);

        // Referencias a los elementos del layout
        editId = view.findViewById(R.id.editId);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        btnEliminar = view.findViewById(R.id.btnEliminar);

        dbHelper = new DatabaseHelper(getContext());

        // Acción del botón "Buscar"
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(editId.getText().toString());
                Empleados empleado = dbHelper.getEmpleado(id);
                if (empleado != null) {
                    Toast.makeText(getContext(), "Empleado encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Empleado no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Acción del botón "Eliminar"
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int id = Integer.parseInt(editId.getText().toString());
                    Empleados empleado = dbHelper.getEmpleado(id);
                    if (empleado != null) {
                        dbHelper.deleteEmpleado(id);
                        dbHelper.deletePuesto(empleado.getPuestoDeTrabajo().getId());
                        dbHelper.deleteHorario(empleado.getHorarioLaboral().getId());
                        Toast.makeText(getContext(), "Empleado eliminado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Empleado no encontrado", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "ID inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}