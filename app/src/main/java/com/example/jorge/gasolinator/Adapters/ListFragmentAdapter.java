package com.example.jorge.gasolinator.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorge.gasolinator.Data.Vehiculos;
import com.example.jorge.gasolinator.R;

import java.util.List;

/**
 * Created by jorge on 22/04/17.
 */

public class ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.ListViewHolder> {

    private List <Vehiculos> items;
    private Context context;

    public static class ListViewHolder extends RecyclerView.ViewHolder{

        //Campos que compondrán el item

        public ImageView foto;
        public TextView apodo;
        public TextView marca;
        public TextView modelo;

        public ListViewHolder(View v) {
            super(v);
            foto = (ImageView) v.findViewById(R.id.foto);
            apodo = (TextView) v.findViewById(R.id.apodo);
            marca = (TextView) v.findViewById(R.id.marca);
            modelo = (TextView) v.findViewById(R.id.modelo);
        }
    }

    public ListFragmentAdapter(List <Vehiculos> items, Context context){
        this.items = items;
        this.context = context;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_lista_vehiculos, viewGroup, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListViewHolder viewHolder, int i) {
      /*  //Picasso.with(context).load(new File(items.get(i).getFoto())).into(viewHolder.foto);
        viewHolder.apodo.setText(items.get(i).getApodo());
        viewHolder.marca.setText(items.get(i).getMarca());
        viewHolder.modelo.setText(items.get(i).getModelo());*/
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
