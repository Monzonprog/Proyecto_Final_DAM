package com.example.jorge.gasolinator.Data;

/**
 * Created by jorge on 9/08/17.
 */

public class Vehiculos {

    public String marca;
    public String modelo;
    public String apodo;
    public String tipo;
    public String combustible;
    public String foto_Uri;

    public Vehiculos(String marca, String modelo, String apodo, String tipo, String combustible,
                     String foto_Uri) {

        this.marca = marca;
        this.modelo = modelo;
        this.apodo = apodo;
        this.tipo = tipo;
        this.combustible = combustible;
        this.foto_Uri = foto_Uri;
    }
}