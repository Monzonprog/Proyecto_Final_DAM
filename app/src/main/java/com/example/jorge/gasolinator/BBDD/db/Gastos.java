package com.example.jorge.gasolinator.BBDD.db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "GASTOS".
 */
@Entity
public class Gastos {
    private String idVehiculo;

    @NotNull
    private String tipo_operacion;

    @NotNull
    private String coste;

    @NotNull
    private String acciones;

    @NotNull
    private String foto_uri_gasto;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated(hash = 315446416)
    public Gastos() {
    }

    @Generated(hash = 207852931)
    public Gastos(String idVehiculo, @NotNull String tipo_operacion, @NotNull String coste, @NotNull String acciones,
            @NotNull String foto_uri_gasto) {
        this.idVehiculo = idVehiculo;
        this.tipo_operacion = tipo_operacion;
        this.coste = coste;
        this.acciones = acciones;
        this.foto_uri_gasto = foto_uri_gasto;
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @NotNull
    public String getTipo_operacion() {
        return tipo_operacion;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTipo_operacion(@NotNull String tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    @NotNull
    public String getCoste() {
        return coste;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCoste(@NotNull String coste) {
        this.coste = coste;
    }

    @NotNull
    public String getAcciones() {
        return acciones;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setAcciones(@NotNull String acciones) {
        this.acciones = acciones;
    }

    @NotNull
    public String getFoto_uri_gasto() {
        return foto_uri_gasto;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setFoto_uri_gasto(@NotNull String foto_uri_gasto) {
        this.foto_uri_gasto = foto_uri_gasto;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
