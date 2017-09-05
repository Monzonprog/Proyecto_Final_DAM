package com.example.jorge.gasolinator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jorge.gasolinator.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //Método para llamar a la actividad vehiculos
    public void vehiculos(View v) {
        Intent i = new Intent(this, VehiculosActivity.class);
        startActivity(i);
    }
    //Método para llamar a la actividad respostaje
    public void repostaje(View v) {
        Intent i = new Intent(this, RepostajeActivity.class);
        startActivity(i);
    }

    //Método para llamar a la actividad mantenimiento
    public void mantenimiento(View v) {
        Intent i = new Intent(this, MantenimientoActivity.class);
        startActivity(i);
    }

    //Método para llamar a la actividad impuesto/gastos
    public void impuestos(View v) {
        Intent i = new Intent(this, ImpuestosActivity.class);
        startActivity(i);
    }
    //Método para llamar a la actividad de mapas donde veremos las gasolineras cercanas
    public void gasolineras(View v) {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    //Método para salir de la aplicación
    public void salirAplicacion(View v) {
        finish();
    }


}
