package com.example.jorge.gasolinator.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jorge.gasolinator.BBDD.db.Repostaje;
import com.example.jorge.gasolinator.R;

import java.util.List;

/**
 * Created by jorge on 5/09/17.
 */

public class ListaRepostajeAdapter extends RecyclerView.Adapter<ListaRepostajeAdapter.RepostajeViewHolder> {

    private List<Repostaje> items;
    public Context context;


    public static class RepostajeViewHolder extends RecyclerView.ViewHolder {

        //Elementos del ViewHolder
        private TextView costeTarjetaRepostaje, tipoLlenadoTarjetaRepostaje, fechaTarjetaRepostaje;


        public RepostajeViewHolder(View v) {
            super(v);

            costeTarjetaRepostaje = (TextView)v.findViewById(R.id.costeTarjetaRepostaje);
            tipoLlenadoTarjetaRepostaje = (TextView)v.findViewById(R.id.tipoLlenadoTarjetaRepostaje);
            fechaTarjetaRepostaje = (TextView)v.findViewById(R.id.fechaTarjetaRepostaje);

        }
    }

    //Recibimos los elementos
    public ListaRepostajeAdapter(List<Repostaje> items) {

        this.items = items;

    }

    //Obtenemos la cantidad de elmentos
    @Override
    public int getItemCount() {
        return items.size();
    }

    //Creamos el ViewHolder
    @Override
    public RepostajeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.tarjeta_repostaje, viewGroup, false);

        context = viewGroup.getContext();

        return new RepostajeViewHolder(v);


    }

    //Pintamos los elementos del ViewHolder
    @Override
    public void onBindViewHolder(RepostajeViewHolder viewholder, final int i) {

        String coste = items.get(i).getCosteRepostaje() + "€ - " + items.get(i).getLitrosRepostaje()
                + "L a " + items.get(i).getPrecioLitroRepostaje() + " €/L";

        String fecha = items.get(i).getDiaRepostaje() + " - " + items.get(i).getMesRepostaje()
                + " - " + items.get(i).getAñoRepostaje();

        String llenado = "Tipo de llenado: " + items.get(i).getTipoLlenado();


        viewholder.costeTarjetaRepostaje.setText(coste);
        viewholder.tipoLlenadoTarjetaRepostaje.setText(llenado);
        viewholder.fechaTarjetaRepostaje.setText(fecha);




    }
}