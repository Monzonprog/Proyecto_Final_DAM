package com.example.jorge.gasolinator.Interfaces;

import com.example.jorge.gasolinator.Class.GasolinerasCercanasObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jorge on 4/09/17.
 */

public interface RestClient {

    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyAOWdJ_I5YBEHj_n5BedjU1nNmFDfOpAQ4")
    Call<GasolinerasCercanasObject> getGasolinerasCercanas(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);
}
