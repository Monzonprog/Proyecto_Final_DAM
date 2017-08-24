package com.example.jorge.gasolinator.Fragments;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.jorge.gasolinator.Adapters.ListaVehiculosAdapter;
import com.example.jorge.gasolinator.BBDD.db.DaoMaster;
import com.example.jorge.gasolinator.BBDD.db.DaoSession;
import com.example.jorge.gasolinator.BBDD.db.Vehiculos;
import com.example.jorge.gasolinator.R;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaVehiculosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaVehiculosFragment extends Fragment {

    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;


    private ListaVehiculosAdapter adapter;
    private List<Vehiculos> vehiculos;

    private SQLiteDatabase db;


    private DaoMaster daoMaster;
    private DaoSession daoSession;


    public ListaVehiculosFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListaVehiculosFragment newInstance() {
        ListaVehiculosFragment fragment = new ListaVehiculosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pintarListaVehiculos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_vehiculos, container, false);

        /*daoSession = ((AppController) getApplication()).getDaoSession();

        List<Vehiculos> vehiculos = new ArrayList<>();*/


    }

    private void pintarListaVehiculos() {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),"Vehiculos-db");
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        daoSession.getVehiculosDao();

        recycler = (RecyclerView) getActivity().findViewById(R.id.recicladorFragmentListaVehiculos);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new ListaVehiculosAdapter(vehiculos);
        //adapter.setListener(getActivity());
        recycler.setAdapter(adapter);

    }
}
