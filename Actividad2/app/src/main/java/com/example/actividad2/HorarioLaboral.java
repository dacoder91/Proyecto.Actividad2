package com.example.actividad2;

import java.sql.Time;

//Clase para el horario laboral de un empleado
public class HorarioLaboral {
    private int id;
    private Time horarioEntrada;
    private Time horarioSalida;
    private int diasLaborales;


    public HorarioLaboral(int id, Time horarioEntrada, Time horarioSalida, int diasLaborales) {
        this.id = id;
        this.horarioEntrada = horarioEntrada;
        this.horarioSalida = horarioSalida;
        this.diasLaborales = diasLaborales;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(Time horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public Time getHorarioSalida() {
        return horarioSalida;
    }

    public void setHorarioSalida(Time horarioSalida) {
        this.horarioSalida = horarioSalida;
    }

    public int getDiasLaborales() {
        return diasLaborales;
    }

    public void setDiasLaborales(int diasLaborales) {
        this.diasLaborales = diasLaborales;
    }
}