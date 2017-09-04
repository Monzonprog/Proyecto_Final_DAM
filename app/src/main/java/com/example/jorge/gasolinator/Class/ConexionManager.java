package com.example.jorge.gasolinator.Class;

import android.util.Log;

import com.example.jorge.gasolinator.Interfaces.IDataGasolinerasCercanas;
import com.example.jorge.gasolinator.Interfaces.RestClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jorge on 4/09/17.
 */

public class ConexionManager {
    private Gson gson;
    private Retrofit retrofit;
    private RestClient restClient;
    private IDataGasolinerasCercanas listenerGasolineras;

    public ConexionManager(String url) {

        gson = new GsonBuilder()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        restClient = retrofit.create(RestClient.class);
    }

    public void getGasolinerasCercanas(IDataGasolinerasCercanas listener, String type, double latitude, double longitude, int PROXIMITY_RADIUS){
        listenerGasolineras = listener;
        Call<GasolinerasCercanasObject> call = restClient.getGasolinerasCercanas(type, latitude + "," + longitude, PROXIMITY_RADIUS);
        call.enqueue(new Callback<GasolinerasCercanasObject>() {
            @Override
            public void onResponse(Call<GasolinerasCercanasObject> call, Response<GasolinerasCercanasObject> response) {
                switch (response.code()) {
                    case 200:
                        GasolinerasCercanasObject data = response.body();
                        listenerGasolineras.conexionCorrecta(data);
                        break;
                    case 401:
                        listenerGasolineras.conexionIncorrecta();
                        break;
                    default:
                        listenerGasolineras.conexionIncorrecta();

                        break;

                }
            }
                    @Override
                    public void onFailure(Call<GasolinerasCercanasObject> call, Throwable t) {

                        listenerGasolineras.conexionIncorrecta();

                        Log.e("error", t.toString());

                    }

                });

            }

}
