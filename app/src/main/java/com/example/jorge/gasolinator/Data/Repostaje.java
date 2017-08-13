package com.example.jorge.gasolinator.Data;

/**
 * Created by jorge on 10/08/17.
 */

public class Repostaje {

    public String id_vehiculo;
    public String vehiculo;
    public String km;
    public String tipo_llenado;
    public String coste;
    public String precio_litro;
    public String foto_uri_repostaje;


    public Repostaje(String id_vehiculo, String vehiculo, String km, String tipo_llenado,
                     String coste, String precio_litro, String foto_uri_repostaje) {

        this.id_vehiculo = id_vehiculo;
        this.vehiculo = vehiculo;
        this.km = km;
        this.tipo_llenado = tipo_llenado;
        this.coste = coste;
        this.precio_litro = precio_litro;
        this.foto_uri_repostaje = foto_uri_repostaje;
    }
}
