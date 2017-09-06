package com.example.jorge.gasolinator.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorge.gasolinator.BBDD.db.Gastos;
import com.example.jorge.gasolinator.Interfaces.VerFactura;
import com.example.jorge.gasolinator.R;

import java.util.List;

/**
 * Created by jorge on 7/09/17.
 */

public class ListaMantenimientoAdapter  extends RecyclerView.Adapter<ListaMantenimientoAdapter.GastosViewHolder> {

    private List<Gastos> items;
    public Context context;
    private VerFactura listener;


    public static class GastosViewHolder extends RecyclerView.ViewHolder {

        //Elementos del ViewHolder
        private TextView costeTarjetaGasto, tipoOperacionTarjetaGasto, fechaTarjetaGastos, descripcionOperacionTarjetaGasto;
        private ImageView detalleTarjetaGasto;


        public GastosViewHolder(View v) {
            super(v);

            costeTarjetaGasto = (TextView)v.findViewById(R.id.costeTarjetaGasto);
            tipoOperacionTarjetaGasto = (TextView)v.findViewById(R.id.tipoOperacionTarjetaGasto);
            fechaTarjetaGastos = (TextView)v.findViewById(R.id.fechaTarjetaGastos);
            descripcionOperacionTarjetaGasto = (TextView)v.findViewById(R.id.descripcionOperacionTarjetaGasto);
            detalleTarjetaGasto = (ImageView)v.findViewById(R.id.detalleTarjetaGasto);
        }
    }

    //Recibimos los elementos
    public ListaMantenimientoAdapter(List<Gastos> items) {

        this.items = items;

    }

    //Obtenemos la cantidad de elmentos
    @Override
    public int getItemCount() {
        return items.size();
    }

    //Creamos el ViewHolder
    @Override
    public GastosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.tarjeta_gastos, viewGroup, false);

        context = viewGroup.getContext();

        return new GastosViewHolder(v);


    }

    //Pintamos los elementos del ViewHolder
    @Override
    public void onBindViewHolder(GastosViewHolder viewholder, final int i) {

        String coste = items.get(i).getCoste() + "€";

        String fecha = items.get(i).getDiaGastos() + " - " + items.get(i).getMesGastos()
                + " - " + items.get(i).getAñoGastos();

        String tipo = items.get(i).getTipo_operacion();
        String descripcion = items.get(i).getAcciones();

        final String uri = items.get(i).getFoto_uri_gasto();


        viewholder.costeTarjetaGasto.setText(coste);
        viewholder.tipoOperacionTarjetaGasto.setText(tipo);
        viewholder.fechaTarjetaGastos.setText(fecha);
        viewholder.descripcionOperacionTarjetaGasto.setText(descripcion);

        if(uri.equals("")){ //Si no hay imagen guardada mostramos el icono con un color grisaceo
            viewholder.detalleTarjetaGasto.setImageResource(R.drawable.sin_detalle);}

        viewholder.detalleTarjetaGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.examinarFactura(uri);

            }
        });

    }

    //Listener de la cardview
    public void setListener (VerFactura listener){

        this.listener = listener;
    }
}
