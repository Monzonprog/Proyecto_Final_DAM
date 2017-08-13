package com.example.jorge.gasolinator.SQLite;

/**
 * Created by jorge on 10/08/17.
 */

public class ContratoGestion {

    interface ColumnasVehiculo {

        String ID = "id";
        String MARCA = "marca";
        String MODELO = "modelo";
        String APODO = "apodo";
        String TIPO = "tipo";
        String COMBUSTIBLE = "combustible";
        String FOTO_URI_VEHICULO = "foto_uri_vehiculo";
    }

    interface ColumnasRepostaje{

        String ID_VEHICULO = "id_vehiculo";
        String VEHICULO = "vehiculo";
        String KM = "km";
        String TIPO_LLENADO = "tipo_llenado";
        String COSTE = "coste";
        String PRECIO_LITRO = "precio_litro";
        String FOTO_URI_RESPOTAJE = "foto_uri_repostaje";
    }

    interface ColumnasGastos{

        String ID_VEHICULO = "id_vehiculo";
        String VEHICULO = "vehiculo";
        String TIPO_OPERACION = "tipo_operacion";
        String COSTE = "coste";
        String ACCIONES = "acciones";
        String FOTO_URI_GASTO = "foto_uri_gasto";
    }

    interface ColumnaImpuestos{

        String ID_VEHICULO = "id_vehiculo";
        String VEHICULO = "vehiculo";
        String CONCEPTO = "concepto";
        String COSTE = "coste";
        String DESCRIPCION = "descripcion";
        String FOTO_URI_IMPUESTO = "foto_uri_impuesto";

    }

    public static class Vehiculos implements ColumnasVehiculo{
        public static void CrearVehiculo(){}
    }

    public static class Repostaje implements ColumnasRepostaje{
        public static void CrearRepostaje(){}
    }

    public static class Gastos implements ColumnasGastos{
        public static void CrearGastos(){}
    }

    public static class Impuestos implements ColumnaImpuestos{
        public static void CrearImpuestos(){}
    }

    private ContratoGestion(){

    }


}
