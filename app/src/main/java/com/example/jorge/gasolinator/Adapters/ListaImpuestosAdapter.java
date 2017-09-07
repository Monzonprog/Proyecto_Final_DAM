package com.example.jorge.gasolinator.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorge.gasolinator.BBDD.db.Impuestos;
import com.example.jorge.gasolinator.Interfaces.VerFactura;
import com.example.jorge.gasolinator.R;

import java.util.List;

/**
 * Created by Jorge Monzón Manzano on 07/09/2017.
 */

public class ListaImpuestosAdapter extends RecyclerView.Adapter<ListaImpuestosAdapter.ImpuestosViewHolder> {

        private List<Impuestos> items;
        public Context context;
        private VerFactura listener;


        public static class ImpuestosViewHolder extends RecyclerView.ViewHolder {

            //Elementos del ViewHolder
            private TextView costeTarjetaImpuesto, conceptoOperacionTarjetaImpuesto, fechaTarjetaImpuestos, descripcionTarjetaImpuesto;
            private ImageView detalleTarjetaImpuesto;


            public ImpuestosViewHolder(View v) {
                super(v);

                costeTarjetaImpuesto = (TextView)v.findViewById(R.id.costeTarjetaImpuesto);
                descripcionTarjetaImpuesto = (TextView)v.findViewById(R.id.descripcionTarjetaImpuesto);
                fechaTarjetaImpuestos = (TextView)v.findViewById(R.id.fechaTarjetaImpuestos);
                conceptoOperacionTarjetaImpuesto = (TextView)v.findViewById(R.id.conceptoOperacionTarjetaImpuesto);
                detalleTarjetaImpuesto = (ImageView)v.findViewById(R.id.detalleTarjetaImpuesto);
            }
        }

        //Recibimos los elementos
    public ListaImpuestosAdapter(List<Impuestos> items) {

            this.items = items;

        }

        //Obtenemos la cantidad de elmentos
        @Override
        public int getItemCount() {
            return items.size();
        }

        //Creamos el ViewHolder
        @Override
        public ListaImpuestosAdapter.ImpuestosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View v = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.tarjeta_impuestos, viewGroup, false);

            context = viewGroup.getContext();

            return new ListaImpuestosAdapter.ImpuestosViewHolder(v);


        }

        //Pintamos los elementos del ViewHolder
        @Override
        public void onBindViewHolder(ListaImpuestosAdapter.ImpuestosViewHolder viewholder, final int i) {

            String coste = "Importe: " + items.get(i).getCoste() + "€";

            String fecha = items.get(i).getDiaImpuestos() + " - " + items.get(i).getMesImpuestos()
                    + " - " + items.get(i).getAñoImpuestos();

            String concepto = items.get(i).getConcepto();
            String descripcion = items.get(i).getDescripcion();

            final String uri = items.get(i).getFoto_uri_impuesto();


            viewholder.costeTarjetaImpuesto.setText(coste);
            viewholder.conceptoOperacionTarjetaImpuesto.setText(concepto);
            viewholder.fechaTarjetaImpuestos.setText(fecha);
            viewholder.descripcionTarjetaImpuesto.setText(descripcion);

            if(uri.equals("")){ //Si no hay imagen guardada mostramos el icono con un color grisaceo
                viewholder.detalleTarjetaImpuesto.setImageResource(R.drawable.sin_detalle);}

            viewholder.detalleTarjetaImpuesto.setOnClickListener(new View.OnClickListener() {
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
