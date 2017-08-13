package com.example.jorge.gasolinator.Data;

/**
 * Created by jorge on 10/08/17.
 */

public class Impuestos {

    public String id_vehiculo;
    public String vehiculo;
    public String concepto;
    public String coste;
    public String descripcion;
    public String foto_uri_impuesto;

    public Impuestos(String id_vehiculo, String vehiculo, String concepto, String coste,
                     String descripcion, String foto_uri_impuesto) {

        this.id_vehiculo = id_vehiculo;
        this.vehiculo = vehiculo;
        this.concepto = concepto;
        this.coste = coste;
        this.descripcion = descripcion;
        this.foto_uri_impuesto = foto_uri_impuesto;

    }
}
