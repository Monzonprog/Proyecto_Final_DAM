package com.example.jorge.gasolinator.Fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jorge.gasolinator.R;

/**
 * Created by jorge on 10/04/17.
 */

public class VehiculosFragment extends Fragment  {

    private TextInputEditText marca;
    private TextInputEditText modelo;
    private TextInputEditText apodo;
    private Spinner tipo;
    private Spinner combustible;

    public static VehiculosFragment newInstance() {
        Bundle args = new Bundle();
        VehiculosFragment fragment = new VehiculosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.nuevo_vehiculo_fragment, container, false);

        return view;


    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        marca = (TextInputEditText)getActivity().findViewById(R.id.marcaVehiculo);
        modelo = (TextInputEditText)getActivity().findViewById(R.id.modeloVehiculo);
        apodo = (TextInputEditText)getActivity().findViewById(R.id.apodoVehiculo);
        tipo = (Spinner)getActivity().findViewById(R.id.tipoVehiculo);
        combustible = (Spinner)getActivity().findViewById(R.id.combustibleVehiculo);


        tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = tipo.getSelectedItem().toString();
                Toast.makeText(getActivity(),"Selección actual: "+ text, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        combustible.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = combustible.getSelectedItem().toString();
                Toast.makeText(getActivity(),"Selección actual: "+ text, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}
