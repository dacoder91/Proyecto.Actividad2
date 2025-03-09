package com.example.actividad2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EmpleadoAdapter extends RecyclerView.Adapter<EmpleadoAdapter.EmpleadoViewHolder> {

    private List<Empleados> listaEmpleados;

    public EmpleadoAdapter(List<Empleados> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    @NonNull
    @Override
    public EmpleadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empleado, parent, false);
        return new EmpleadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpleadoViewHolder holder, int position) {
        Empleados empleado = listaEmpleados.get(position);

        // Mostrar el ID del empleado
        holder.txtId.setText("ID: " + empleado.getId());

        // Mostrar el nombre completo
        String nombreCompleto = empleado.getNombre() + " " + empleado.getPrimerApellido() + " " + empleado.getSegundoApellido();
        holder.txtNombreCompleto.setText(nombreCompleto);

        // Mostrar el DNI
        holder.txtDni.setText("DNI: " + empleado.getDni());

        // Mostrar la fecha de nacimiento
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String fechaNacimiento = sdf.format(empleado.getFechaNacimiento());
        holder.txtFechaNacimiento.setText("Fecha de Nacimiento: " + fechaNacimiento);

        // Mostrar la dirección
        holder.txtDireccion.setText("Dirección: " + empleado.getDireccion());

        // Mostrar el puesto de trabajo
        holder.txtPuesto.setText("Puesto: " + empleado.getPuestoDeTrabajo().getNombre());

        // Mostrar la descripción del puesto
        holder.txtDescripcionPuesto.setText("Descripción: " + empleado.getPuestoDeTrabajo().getDescripcion());

        // Mostrar el horario laboral
        String horario = "Horario: " + empleado.getHorarioLaboral().getHorarioEntrada() + " - " + empleado.getHorarioLaboral().getHorarioSalida();
        holder.txtHorario.setText(horario);

        // Mostrar los días laborales
        holder.txtDiasLaborales.setText("Días Laborales: " + empleado.getHorarioLaboral().getDiasLaborales());
    }

    @Override
    public int getItemCount() {
        return listaEmpleados.size();
    }

    public static class EmpleadoViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtNombreCompleto, txtDni, txtFechaNacimiento, txtDireccion, txtPuesto, txtDescripcionPuesto, txtHorario, txtDiasLaborales;

        public EmpleadoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            txtNombreCompleto = itemView.findViewById(R.id.txtNombreCompleto);
            txtDni = itemView.findViewById(R.id.txtDni);
            txtFechaNacimiento = itemView.findViewById(R.id.txtFechaNacimiento);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);
            txtPuesto = itemView.findViewById(R.id.txtPuesto);
            txtDescripcionPuesto = itemView.findViewById(R.id.txtDescripcionPuesto);
            txtHorario = itemView.findViewById(R.id.txtHorario);
            txtDiasLaborales = itemView.findViewById(R.id.txtDiasLaborales);
        }
    }
}