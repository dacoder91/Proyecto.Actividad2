package com.example.actividad2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

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

        // Mostrar el puesto de trabajo
        holder.txtPuesto.setText(empleado.getPuestoDeTrabajo().getNombre());

        // Mostrar el horario laboral
        String horario = empleado.getHorarioLaboral().getHorarioEntrada() + " - " + empleado.getHorarioLaboral().getHorarioSalida();
        holder.txtHorario.setText(horario);
    }

    @Override
    public int getItemCount() {
        return listaEmpleados.size();
    }

    public static class EmpleadoViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtNombreCompleto, txtPuesto, txtHorario;

        public EmpleadoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId); // Nuevo TextView para el ID
            txtNombreCompleto = itemView.findViewById(R.id.txtNombreCompleto);
            txtPuesto = itemView.findViewById(R.id.txtPuesto);
            txtHorario = itemView.findViewById(R.id.txtHorario);
        }
    }
}
