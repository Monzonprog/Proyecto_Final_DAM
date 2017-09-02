package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyGenerator {

    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.example.jorge.gasolinator.BBDD.db");
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        addVehiculosEntities(schema);
        addRepostajeEntities(schema);
        addImpuestosEntities(schema);
        addGastosEntities(schema);

    }

    private static Entity addVehiculosEntities(final Schema schema) {
        Entity vehiculos = schema.addEntity("Vehiculos");
        vehiculos.addIdProperty().autoincrement().primaryKey();
        vehiculos.addStringProperty("marca").notNull();
        vehiculos.addStringProperty("modelo").notNull();
        vehiculos.addStringProperty("apodo").notNull();
        vehiculos.addStringProperty("tipo").notNull();
        vehiculos.addStringProperty("combustible").notNull();
        vehiculos.addStringProperty("foto_Uri");
        return vehiculos;
    }

    private static Entity addRepostajeEntities(Schema schema) {

        Entity repostaje = schema.addEntity("Repostaje");
        repostaje.addStringProperty("idVehiculo").notNull();;
        repostaje.addStringProperty("tipoLlenado").notNull();
        repostaje.addStringProperty("kmActuales").notNull();
        repostaje.addStringProperty("costeRepostaje").notNull();
        repostaje.addStringProperty("litrosRepostaje").notNull();
        repostaje.addStringProperty("precioLitroRepostaje").notNull();
        repostaje.addStringProperty("diaRepostaje").notNull();
        repostaje.addStringProperty("mesRepostaje").notNull();
        repostaje.addStringProperty("añoRepostaje").notNull();
        repostaje.addStringProperty("foto_Uri");
        return repostaje;
    }

    private static Entity addImpuestosEntities(Schema schema) {

        Entity impuestos = schema.addEntity("Impuestos");
        impuestos.addStringProperty("idVehiculo");
        impuestos.addStringProperty("concepto").notNull();
        impuestos.addStringProperty("coste").notNull();
        impuestos.addStringProperty("descripcion").notNull();
        impuestos.addStringProperty("diaImpuestos").notNull();
        impuestos.addStringProperty("mesImpuestos").notNull();
        impuestos.addStringProperty("añoImpuestos").notNull();
        impuestos.addStringProperty("foto_uri_impuesto");
        return impuestos;
    }

    private static Entity addGastosEntities(Schema schema) {

        Entity gastos = schema.addEntity("Gastos");
        gastos.addStringProperty("idVehiculo");
        gastos.addStringProperty("tipo_operacion").notNull();
        gastos.addStringProperty("coste").notNull();
        gastos.addStringProperty("acciones").notNull();
        gastos.addStringProperty("diaGastos").notNull();
        gastos.addStringProperty("mesGastos").notNull();
        gastos.addStringProperty("añoGastos").notNull();
        gastos.addStringProperty("foto_uri_gasto");
        return gastos;
    }

}