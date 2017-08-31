package com.example.jorge.gasolinator.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorge.gasolinator.BBDD.db.Vehiculos;
import com.example.jorge.gasolinator.Interfaces.OpcionesTarjetaVehiculos;
import com.example.jorge.gasolinator.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static android.support.constraint.R.id.parent;


/**
 * Created by jorge on 24/08/17.
 */

public class ListaVehiculosAdapter extends RecyclerView.Adapter<ListaVehiculosAdapter.VehiculosViewHolder> {

    private List<Vehiculos> items;
    public Context context;
    private OpcionesTarjetaVehiculos listener;


    public static class VehiculosViewHolder extends RecyclerView.ViewHolder {

    public ImageView imagenTarjetaVehiculo;
    public TextView textViewMarcaTarjetaVehiculo;
    public TextView textViewModeloTarjetaVehiculo;
    public TextView textViewApodoTarjetaVehiculo;
    public ImageView editarTarjetaVehiculo;
    public ImageView borrarTarjetaVehiculo;

    public VehiculosViewHolder(View v) {
        super(v);
        imagenTarjetaVehiculo = (ImageView) v.findViewById(R.id.imagenTarjetaVehiculo);
        textViewMarcaTarjetaVehiculo = (TextView)v.findViewById(R.id.textViewMarcaTarjetaVehiculo);
        textViewModeloTarjetaVehiculo = (TextView)v.findViewById(R.id.textViewModeloTarjetaVehiculo);
        textViewApodoTarjetaVehiculo = (TextView)v.findViewById(R.id.textViewApodoTarjetaVehiculo);
        editarTarjetaVehiculo = (ImageView) v.findViewById(R.id.editarTarjetaVehiculo);
        borrarTarjetaVehiculo = (ImageView) v.findViewById(R.id.borrarTarjetaVehiculo);
    }
}

    public ListaVehiculosAdapter(List<Vehiculos> items){

        this.items = items;

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public VehiculosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.tarjeta_vehiculo, viewGroup, false);

        context = viewGroup.getContext();

        return new VehiculosViewHolder(v);


    }

    @Override
    public void onBindViewHolder(VehiculosViewHolder viewholder, final int i) {

        if(items.get(i).getFoto_Uri().toString().equals("")){
            Picasso.with(context).load(R.drawable.siluetas_vehiculos)
                    .into( viewholder.imagenTarjetaVehiculo);
        }else{Picasso.with(context).load(items.get(i).getFoto_Uri()).transform(new CropCircleTransformation())
                .into( viewholder.imagenTarjetaVehiculo);}
        viewholder.textViewMarcaTarjetaVehiculo.setText(items.get(i).getMarca());
        viewholder.textViewModeloTarjetaVehiculo.setText(items.get(i).getModelo());
        viewholder.textViewApodoTarjetaVehiculo.setText(items.get(i).getApodo());



        viewholder.borrarTarjetaVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.eliminarVehiculo(convertirValor(i));
            }
        });

        viewholder.editarTarjetaVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.modificarVehiculo(convertirValor(i));

            }
        });

    }

    private String convertirValor(int i){

        String ID = String.valueOf(items.get(i).getId());

        return ID;

    }

    public void setListener (OpcionesTarjetaVehiculos listener){

        this.listener = listener;
    }


}


