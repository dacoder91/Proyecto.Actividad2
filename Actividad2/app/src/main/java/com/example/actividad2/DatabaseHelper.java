package com.example.actividad2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "empleados.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla Empleados
    private static final String TABLE_EMPLEADOS = "empleados";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_PRIMER_APELLIDO = "primerApellido";
    private static final String COLUMN_SEGUNDO_APELLIDO = "segundoApellido";
    private static final String COLUMN_DNI = "dni";
    private static final String COLUMN_FECHA_NACIMIENTO = "fechaNacimiento";
    private static final String COLUMN_DIRECCION = "direccion";
    private static final String COLUMN_PUESTO_ID = "puestoId";
    private static final String COLUMN_HORARIO_ID = "horarioId";

    // Tabla Puestos de Trabajo
    private static final String TABLE_PUESTOS = "puestos";
    private static final String COLUMN_PUESTO_NOMBRE = "nombre";
    private static final String COLUMN_PUESTO_DESCRIPCION = "descripcion";

    // Tabla Horarios Laborales
    private static final String TABLE_HORARIOS = "horarios";
    private static final String COLUMN_HORARIO_ENTRADA = "horarioEntrada";
    private static final String COLUMN_HORARIO_SALIDA = "horarioSalida";
    private static final String COLUMN_DIAS_LABORALES = "diasLaborales";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Al crear la base de datos por primera vez, se crean las tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    // Si se actualiza la versión de la base de datos, se actualizan las tablas
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes implementar la lógica de migración si cambias la estructura de la base de datos
        // Por ahora, simplemente borramos y volvemos a crear las tablas
        dropTables(db);
        onCreate(db);
    }

    // Metodo para crear las tablas
    private void createTables(SQLiteDatabase db) {
        // Crear tabla Puestos de Trabajo
        String CREATE_PUESTOS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PUESTOS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PUESTO_NOMBRE + " TEXT,"
                + COLUMN_PUESTO_DESCRIPCION + " TEXT)";
        db.execSQL(CREATE_PUESTOS_TABLE);

        // Crear tabla Horarios Laborales
        String CREATE_HORARIOS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_HORARIOS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_HORARIO_ENTRADA + " TEXT,"
                + COLUMN_HORARIO_SALIDA + " TEXT,"
                + COLUMN_DIAS_LABORALES + " INTEGER)";
        db.execSQL(CREATE_HORARIOS_TABLE);

        // Crear tabla Empleados
        String CREATE_EMPLEADOS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLEADOS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOMBRE + " TEXT,"
                + COLUMN_PRIMER_APELLIDO + " TEXT,"
                + COLUMN_SEGUNDO_APELLIDO + " TEXT,"
                + COLUMN_DNI + " TEXT,"
                + COLUMN_FECHA_NACIMIENTO + " TEXT,"
                + COLUMN_DIRECCION + " TEXT,"
                + COLUMN_PUESTO_ID + " INTEGER,"
                + COLUMN_HORARIO_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_PUESTO_ID + ") REFERENCES " + TABLE_PUESTOS + "(" + COLUMN_ID + "),"
                + "FOREIGN KEY(" + COLUMN_HORARIO_ID + ") REFERENCES " + TABLE_HORARIOS + "(" + COLUMN_ID + "))";
        db.execSQL(CREATE_EMPLEADOS_TABLE);
    }

    // Metodo para eliminar las tablas
    private void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLEADOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUESTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HORARIOS);
    }

    // Metodos CRUD para Empleados, Puestos y Horarios

    //Insertar un nuevo empleado
    public long insertEmpleadoCompleto(Empleados empleado, PuestoDeTrabajo puesto, HorarioLaboral horario) {
        SQLiteDatabase db = this.getWritableDatabase();
        long puestoId = -1;
        long horarioId = -1;
        long empleadoId = -1;

        db.beginTransaction();
        try {
            // Insertar puesto
            ContentValues puestoValues = new ContentValues();
            puestoValues.put(COLUMN_PUESTO_NOMBRE, puesto.getNombre());
            puestoValues.put(COLUMN_PUESTO_DESCRIPCION, puesto.getDescripcion());
            puestoId = db.insert(TABLE_PUESTOS, null, puestoValues);

            // Insertar horario
            ContentValues horarioValues = new ContentValues();
            horarioValues.put(COLUMN_HORARIO_ENTRADA, horario.getHorarioEntrada().toString());
            horarioValues.put(COLUMN_HORARIO_SALIDA, horario.getHorarioSalida().toString());
            horarioValues.put(COLUMN_DIAS_LABORALES, horario.getDiasLaborales());
            horarioId = db.insert(TABLE_HORARIOS, null, horarioValues);

            // Insertar empleado
            ContentValues empleadoValues = new ContentValues();
            empleadoValues.put(COLUMN_NOMBRE, empleado.getNombre());
            empleadoValues.put(COLUMN_PRIMER_APELLIDO, empleado.getPrimerApellido());
            empleadoValues.put(COLUMN_SEGUNDO_APELLIDO, empleado.getSegundoApellido());
            empleadoValues.put(COLUMN_DNI, empleado.getDni());

            // Formatear la fecha al nuevo formato dd-MM-yyyy
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            empleadoValues.put(COLUMN_FECHA_NACIMIENTO, sdf.format(empleado.getFechaNacimiento()));

            empleadoValues.put(COLUMN_DIRECCION, empleado.getDireccion());
            empleadoValues.put(COLUMN_PUESTO_ID, puestoId);
            empleadoValues.put(COLUMN_HORARIO_ID, horarioId);
            empleadoId = db.insert(TABLE_EMPLEADOS, null, empleadoValues);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error en transacción", e);
        } finally {
            db.endTransaction();
            db.close();
        }

        return empleadoId;
    }

    // Obtener un empleado por ID
    public Empleados getEmpleado(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Empleados empleado = null;
        Cursor cursor = db.query(TABLE_EMPLEADOS, new String[]{COLUMN_ID, COLUMN_NOMBRE, COLUMN_PRIMER_APELLIDO, COLUMN_SEGUNDO_APELLIDO, COLUMN_DNI, COLUMN_FECHA_NACIMIENTO, COLUMN_DIRECCION, COLUMN_PUESTO_ID, COLUMN_HORARIO_ID},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                // Parsear la fecha al nuevo formato dd-MM-yyyy
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date fechaNacimiento = new Date(sdf.parse(cursor.getString(5)).getTime());

                empleado = new Empleados(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        fechaNacimiento,
                        cursor.getString(6),
                        getPuesto(cursor.getInt(7)),
                        getHorario(cursor.getInt(8))
                );
            } catch (ParseException e) {
                Log.e("DatabaseHelper", "Error al parsear la fecha", e);
            }
            cursor.close();
        }
        db.close();
        return empleado;
    }

    // Obtener un puesto de trabajo por ID
    public PuestoDeTrabajo getPuesto(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PUESTOS, new String[]{
                        COLUMN_ID, COLUMN_PUESTO_NOMBRE, COLUMN_PUESTO_DESCRIPCION
                },
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            PuestoDeTrabajo puesto = new PuestoDeTrabajo(
                    cursor.getInt(0), // ID
                    cursor.getString(1), // Nombre
                    cursor.getString(2)  // Descripción
            );
            cursor.close();
            return puesto;
        }
        return null; // Si no se encuentra el puesto
    }

    // Obtener un horario laboral por ID
    public HorarioLaboral getHorario(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HORARIOS, new String[]{
                        COLUMN_ID, COLUMN_HORARIO_ENTRADA, COLUMN_HORARIO_SALIDA, COLUMN_DIAS_LABORALES
                },
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            HorarioLaboral horario = new HorarioLaboral(
                    cursor.getInt(0), // ID
                    Time.valueOf(cursor.getString(1)), // Horario de Entrada
                    Time.valueOf(cursor.getString(2)), // Horario de Salida
                    cursor.getInt(3)  // Días Laborales
            );
            cursor.close();
            return horario;
        }
        return null; // Si no se encuentra el horario
    }
    // Eliminar un empleado por ID
    public void deleteEmpleado(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLEADOS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Eliminar un puesto de trabajo por ID
    public void deletePuesto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PUESTOS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Eliminar un horario laboral por ID
    public void deleteHorario(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HORARIOS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Actualizar un empleado
    public void updateEmpleado(Empleados empleado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, empleado.getNombre());
        values.put(COLUMN_PRIMER_APELLIDO, empleado.getPrimerApellido());
        values.put(COLUMN_SEGUNDO_APELLIDO, empleado.getSegundoApellido());
        values.put(COLUMN_DNI, empleado.getDni());
        values.put(COLUMN_FECHA_NACIMIENTO, empleado.getFechaNacimiento().toString());
        values.put(COLUMN_DIRECCION, empleado.getDireccion());
        values.put(COLUMN_PUESTO_ID, empleado.getPuestoDeTrabajo().getId());
        values.put(COLUMN_HORARIO_ID, empleado.getHorarioLaboral().getId());

        // Actualizar el empleado
        db.update(TABLE_EMPLEADOS, values, COLUMN_ID + "=?", new String[]{String.valueOf(empleado.getId())});
    }

    // Actualizar un puesto de trabajo
    public void updatePuesto(@NonNull PuestoDeTrabajo puesto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PUESTO_NOMBRE, puesto.getNombre());
        values.put(COLUMN_PUESTO_DESCRIPCION, puesto.getDescripcion());

        // Actualizar el puesto
        db.update(TABLE_PUESTOS, values, COLUMN_ID + "=?", new String[]{String.valueOf(puesto.getId())});
    }

    // Actualizar un horario laboral
    public void updateHorario(@NonNull HorarioLaboral horario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HORARIO_ENTRADA, horario.getHorarioEntrada().toString());
        values.put(COLUMN_HORARIO_SALIDA, horario.getHorarioSalida().toString());
        values.put(COLUMN_DIAS_LABORALES, horario.getDiasLaborales());

        // Actualizar el horario
        db.update(TABLE_HORARIOS, values, COLUMN_ID + "=?", new String[]{String.valueOf(horario.getId())});
    }

    // Obtener todos los empleados
    public List<Empleados> getAllEmpleados() throws FechaInvalidaException {
        List<Empleados> listaEmpleados = new ArrayList<>(); // Lista para almacenar los empleados
        SQLiteDatabase db = this.getReadableDatabase(); // Obtener la base de datos en modo lectura

        // Consultar todos los empleados en la tabla
        Cursor cursor = db.query(TABLE_EMPLEADOS, null, null, null, null, null, null);

        // Recorrer el cursor (resultados de la consulta)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    // Parsear la fecha al nuevo formato dd-MM-yyyy
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    Date fechaNacimiento = new Date(sdf.parse(cursor.getString(5)).getTime());

                    // Crear un objeto Empleados con los datos del cursor
                    Empleados empleado = new Empleados(
                            cursor.getInt(0), // ID
                            cursor.getString(1), // Nombre
                            cursor.getString(2), // Primer Apellido
                            cursor.getString(3), // Segundo Apellido
                            cursor.getString(4), // DNI
                            fechaNacimiento, // Fecha de Nacimiento
                            cursor.getString(6), // Dirección
                            getPuesto(cursor.getInt(7)), // Puesto de Trabajo (obtenido por ID)
                            getHorario(cursor.getInt(8))  // Horario Laboral (obtenido por ID)
                    );
                    listaEmpleados.add(empleado); // Agregar el empleado a la lista
                } catch (ParseException e) {
                    // Lanzar una excepción si la fecha no está en el formato correcto
                    throw new FechaInvalidaException("Error al parsear la fecha: " + cursor.getString(5));
                }
            } while (cursor.moveToNext()); // Mover al siguiente registro

            cursor.close(); // Cerrar el cursor
        }

        db.close(); // Cerrar la base de datos
        return listaEmpleados; // Devolver la lista de empleados
    }
}