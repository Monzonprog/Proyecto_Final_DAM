package com.example.jorge.gasolinator.Data;

/**
 * Created by jorge on 10/08/17.
 */

public class Gastos {

    public String id_vehiculo;
    public String vehiculo;
    public String tipo_operacion;
    public String coste;
    public String acciones;
    public String foto_uri_gasto;

    public Gastos(String id_vehiculo, String vehiculo, String tipo_operacion, String coste,
                  String acciones, String foto_uri_gasto) {

        this.id_vehiculo = id_vehiculo;
        this.vehiculo = vehiculo;
        this.tipo_operacion = tipo_operacion;
        this.coste = coste;
        this.acciones = acciones;
        this.foto_uri_gasto = foto_uri_gasto;
    }
}
