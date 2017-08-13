package com.example.jorge.gasolinator.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;

import com.example.jorge.gasolinator.SQLite.ContratoGestion.Vehiculos;
import com.example.jorge.gasolinator.SQLite.ContratoGestion.Repostaje;
import com.example.jorge.gasolinator.SQLite.ContratoGestion.Gastos;
import com.example.jorge.gasolinator.SQLite.ContratoGestion.Impuestos;

/**
 * Created by jorge on 10/08/17.
 */

public class BaseDatosGestion extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "gestion.db";
    private static final int VERSION_ACTUAL = 1;
    private final Context contexto;

    interface Tablas {

        String VEHICULOS ="vehiculos";
        String REPOSTAJE ="repostaje";
        String GASTOS ="gastos";
        String IMPUESTOS="impuestos";
    }

    interface Referencias {

        String ID_VEHICULOS = String.format("REFERENCES %s(%s) ON DELETE CASCADE",
                Tablas.VEHICULOS, Vehiculos.ID);

        String ID_REPOSTAJE = String.format("REFERENCES %s(%s)",
                Tablas.REPOSTAJE, Repostaje.ID_VEHICULO);

        String ID_GASTOS = String.format("REFERENCES %s(%s)",
                Tablas.GASTOS, Gastos.ID_VEHICULO);

        String ID_IMPUESTOS = String.format("REFERENCES %s(%s)",
                Tablas.IMPUESTOS, Impuestos.ID_VEHICULO);
    }

    public BaseDatosGestion(Context contexto){

        super(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = contexto;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL," +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT)",
                Tablas.VEHICULOS, BaseColumns._ID,
                Vehiculos.ID,Vehiculos.MARCA, Vehiculos.MODELO, Vehiculos.APODO,
                Vehiculos.TIPO, Vehiculos.COMBUSTIBLE,Vehiculos.FOTO_URI_VEHICULO));

        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER," +
                        "%s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL," +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT)",
                Tablas.REPOSTAJE, BaseColumns._ID,
                Repostaje.ID_VEHICULO, Repostaje.VEHICULO, Repostaje.KM, Repostaje.TIPO_LLENADO,
                Repostaje.COSTE, Repostaje.PRECIO_LITRO, Repostaje.FOTO_URI_RESPOTAJE));

        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER," +
                        "%s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL," +
                        "%s TEXT NOT NULL, %s TEXT)",
                Tablas.GASTOS, BaseColumns._ID,
                Gastos.ID_VEHICULO, Gastos.VEHICULO, Gastos.TIPO_OPERACION, Gastos.COSTE,
                Gastos.ACCIONES, Gastos.FOTO_URI_GASTO));

        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER," +
                        "%s TEXT NOT NULL,%s TEXT NOT NULL, %s TEXT NOT NULL," +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT)",
                Tablas.IMPUESTOS, BaseColumns._ID,
                Impuestos.ID_VEHICULO, Impuestos.VEHICULO, Impuestos.CONCEPTO, Impuestos.COSTE,
                Impuestos.DESCRIPCION, Impuestos.FOTO_URI_IMPUESTO));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Tablas.VEHICULOS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.REPOSTAJE);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.GASTOS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.IMPUESTOS);

        onCreate(db);

    }
}
