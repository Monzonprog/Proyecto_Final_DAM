package com.example.jorge.gasolinator.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jorge.gasolinator.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends Activity {

    public String[] frases;
    public TextView frasesTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        //Llamamos al método para pintar las frases
        frases = getResources().getStringArray(R.array.frases);
        pintarFrases(frases);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Comenzamos la MainActivity
                Intent mainIntent = new Intent().setClass(
                        SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };

        // Tiempo que permanecerá en pantalla antes de mostrar la MainActivity
        Timer timer = new Timer();
        timer.schedule(task, 5000);
    }

    //Método para pintar en la pantalla las frases
    private void pintarFrases(String[] frases) {

        frasesTV = (TextView) findViewById(R.id.frasesTV);
        frasesTV.setText(frases[aleatorio()]);
    }

    //Método para la obtención de un número aleatorio
    private int aleatorio() {

        Random r = new Random();
        int aleatorio = r.nextInt(4);

        return aleatorio;

    }

}
