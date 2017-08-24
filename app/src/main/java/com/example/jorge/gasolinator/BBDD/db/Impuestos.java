package com.example.jorge.gasolinator.BBDD.db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "IMPUESTOS".
 */
@Entity
public class Impuestos {
    private String idVehiculo;

    @NotNull
    private String concepto;

    @NotNull
    private String coste;

    @NotNull
    private String descripcion;
    private String foto_uri_impuesto;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated(hash = 1225475835)
    public Impuestos() {
    }

    @Generated(hash = 1733668326)
    public Impuestos(String idVehiculo, @NotNull String concepto, @NotNull String coste, @NotNull String descripcion,
            @NotNull String foto_uri_impuesto) {
        this.idVehiculo = idVehiculo;
        this.concepto = concepto;
        this.coste = coste;
        this.descripcion = descripcion;
        this.foto_uri_impuesto = foto_uri_impuesto;
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @NotNull
    public String getConcepto() {
        return concepto;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setConcepto(@NotNull String concepto) {
        this.concepto = concepto;
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
    public String getDescripcion() {
        return descripcion;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDescripcion(@NotNull String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto_uri_impuesto() {
        return foto_uri_impuesto;
    }

    public void setFoto_uri_impuesto(String foto_uri_impuesto) {
        this.foto_uri_impuesto = foto_uri_impuesto;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}