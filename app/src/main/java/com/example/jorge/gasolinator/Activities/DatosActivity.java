package com.example.jorge.gasolinator.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jorge.gasolinator.Adapters.ListaRepostajeAdapter;
import com.example.jorge.gasolinator.Adapters.ListaVehiculosAdapter;
import com.example.jorge.gasolinator.BBDD.db.DaoMaster;
import com.example.jorge.gasolinator.BBDD.db.DaoSession;
import com.example.jorge.gasolinator.BBDD.db.Repostaje;
import com.example.jorge.gasolinator.BBDD.db.RepostajeDao;
import com.example.jorge.gasolinator.BBDD.db.Vehiculos;
import com.example.jorge.gasolinator.BBDD.db.VehiculosDao;
import com.example.jorge.gasolinator.Fragments.ListaVehiculosFragment;
import com.example.jorge.gasolinator.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.jorge.gasolinator.R.id.recicladorRepostajeTarjetaDatos;

public class DatosActivity extends AppCompatActivity {

    private List<Vehiculos> vehiculos;
    private List<Repostaje> repostajes;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private VehiculosDao vehiculosDao;
    private RepostajeDao repostajeDao;
    private Spinner vehiculoSpinnerDatos;
    private ImageView IVExpandirRepostajeTarjetaDatos, IVRecogerRepostajeTarjetaDatos;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private ListaRepostajeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        //Mostramos botón "Atrás" en la activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vehiculoSpinnerDatos = (Spinner)findViewById(R.id.vehiculoSpinnerDatos);
        IVExpandirRepostajeTarjetaDatos = (ImageView)findViewById(R.id.IVExpandirRepostajeTarjetaDatos);
        IVRecogerRepostajeTarjetaDatos = (ImageView)findViewById(R.id.IVRecogerRepostajeTarjetaDatos);


        //Recuperamos datos de los vehiculos creados
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "Vehiculos-db");
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        daoSession.getVehiculosDao();

        vehiculosDao = daoSession.getVehiculosDao();
        vehiculos = vehiculosDao.loadAll();

        //Listas para pintar la lista de vehiculos y después guardar su referencia
        final List<String> coches = new ArrayList<>();
        final List<String> idVehiculoGuardar = new ArrayList<>();

        int i;

        for (i = 0; i < vehiculos.size(); i++) {

            String id = vehiculos.get(i).getId().toString();
            String aux = vehiculos.get(i).getApodo() + " - " + vehiculos.get(i).getMarca();
            idVehiculoGuardar.add(id);
            coches.add(aux);

        }
        //Array para pintar los vehículos
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, coches);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehiculoSpinnerDatos.setAdapter(dataAdapter);

        //Recuperamos datos de repostajes
        daoSession.getRepostajeDao();

        repostajeDao = daoSession.getRepostajeDao();
        repostajes = repostajeDao.loadAll(); //TODO filtrar busqueda a vehiculo seleccionado

        recycler = (RecyclerView) findViewById(recicladorRepostajeTarjetaDatos);


        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        adapter = new ListaRepostajeAdapter(repostajes);
        //adapter.setListener(this); //Listener para el botón de borrar y editar
        recycler.setAdapter(adapter);


        IVExpandirRepostajeTarjetaDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recycler.setVisibility(View.VISIBLE);
                IVRecogerRepostajeTarjetaDatos.setVisibility(View.VISIBLE);
            }
        });

        IVRecogerRepostajeTarjetaDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recycler.setVisibility(View.GONE);
                IVRecogerRepostajeTarjetaDatos.setVisibility(View.GONE);
            }
        });
    }


    //Funcionalidad para el botón "Atrás"
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
