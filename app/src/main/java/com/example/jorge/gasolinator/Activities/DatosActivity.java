package com.example.jorge.gasolinator.Activities;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.jorge.gasolinator.Adapters.ListaImpuestosAdapter;
import com.example.jorge.gasolinator.Adapters.ListaMantenimientoAdapter;
import com.example.jorge.gasolinator.Adapters.ListaRepostajeAdapter;
import com.example.jorge.gasolinator.Adapters.ListaVehiculosAdapter;
import com.example.jorge.gasolinator.BBDD.db.DaoMaster;
import com.example.jorge.gasolinator.BBDD.db.DaoSession;
import com.example.jorge.gasolinator.BBDD.db.Gastos;
import com.example.jorge.gasolinator.BBDD.db.GastosDao;
import com.example.jorge.gasolinator.BBDD.db.Impuestos;
import com.example.jorge.gasolinator.BBDD.db.ImpuestosDao;
import com.example.jorge.gasolinator.BBDD.db.Repostaje;
import com.example.jorge.gasolinator.BBDD.db.RepostajeDao;
import com.example.jorge.gasolinator.BBDD.db.Vehiculos;
import com.example.jorge.gasolinator.BBDD.db.VehiculosDao;
import com.example.jorge.gasolinator.Fragments.ListaVehiculosFragment;
import com.example.jorge.gasolinator.Interfaces.VerFactura;
import com.example.jorge.gasolinator.R;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.example.jorge.gasolinator.R.id.impuestos;
import static com.example.jorge.gasolinator.R.id.recicladorImpuestosTarjetaDatos;
import static com.example.jorge.gasolinator.R.id.recicladorMantenimientiTarjetaDatos;
import static com.example.jorge.gasolinator.R.id.recicladorRepostajeTarjetaDatos;
import static com.example.jorge.gasolinator.R.id.vehiculoSpinnerDatos;

public class DatosActivity extends AppCompatActivity implements VerFactura {

    private List<Vehiculos> vehiculos;
    private List<Repostaje> repostajes;
    private List<Impuestos> impuestos;
    private List<Gastos> gastos;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private VehiculosDao vehiculosDao;
    private RepostajeDao repostajeDao;
    private ImpuestosDao impuestoDao;
    private GastosDao gastoDao;
    private Spinner vehiculoSpinnerDatos, fechaSpinnerDatos;
    private ImageView IVExpandirRepostajeTarjetaDatos,buscarDatosActivity, IVExpandirMantenimientoTarjetaDatos,
            IVExpandirImpuestosTarjetaDatos ;
    private RecyclerView recycler, recycler1, recycler2;
    private RecyclerView.LayoutManager lManager, lManager1, lManager2;
    private ListaRepostajeAdapter adapterRepostaje;
    private ListaMantenimientoAdapter adapterGastos;
    private ListaImpuestosAdapter adapterImpuesto;
    private String id, aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        //Mostramos botón "Atrás" en la activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vehiculoSpinnerDatos = (Spinner) findViewById(R.id.vehiculoSpinnerDatos);
        fechaSpinnerDatos = (Spinner) findViewById(R.id.fechaSpinnerDatos);
        IVExpandirRepostajeTarjetaDatos = (ImageView) findViewById(R.id.IVExpandirRepostajeTarjetaDatos);
        IVExpandirMantenimientoTarjetaDatos = (ImageView) findViewById(R.id.IVExpandirMantenimientoTarjetaDatos);
        IVExpandirImpuestosTarjetaDatos = (ImageView) findViewById(R.id.IVExpandirImpuestosTarjetaDatos);

        buscarDatosActivity = (ImageView) findViewById(R.id.buscarDatosActivity);


        //Recuperamos datos que mostraremos
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(DatosActivity.this, "Vehiculos-db");
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();


        daoSession.getVehiculosDao();
        daoSession.getGastosDao();

        vehiculosDao = daoSession.getVehiculosDao();
        vehiculos = vehiculosDao.loadAll();

        //Listas para pintar la lista de vehiculos y después guardar su referencia
        final List<String> coches = new ArrayList<>();
        final List<String> idVehiculoGuardar = new ArrayList<>();

        int i;

        for (i = 0; i < vehiculos.size(); i++) {

            id = vehiculos.get(i).getId().toString();
            aux = vehiculos.get(i).getApodo() + " - " + vehiculos.get(i).getMarca();
            idVehiculoGuardar.add(id);
            coches.add(aux);

        }
        //Array para pintar los vehículos
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(DatosActivity.this,
                android.R.layout.simple_spinner_item, coches);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehiculoSpinnerDatos.setAdapter(dataAdapter);


        buscarDatosActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (id != null) {

                    String idBuscar = vehiculos.get(vehiculoSpinnerDatos.getSelectedItemPosition()).getId().toString();
                    String mes = String.valueOf(fechaSpinnerDatos.getSelectedItemPosition() + 1);

                    //Recuperamos datos de repostajes
                    daoSession.getRepostajeDao();

                    repostajeDao = daoSession.getRepostajeDao();

                    repostajes = repostajeDao.queryBuilder()
                            .where(RepostajeDao.Properties.IdVehiculo.eq(idBuscar),
                                    RepostajeDao.Properties.MesRepostaje.eq(mes)).build().list();

                    recycler = (RecyclerView) findViewById(recicladorRepostajeTarjetaDatos);


                    lManager = new LinearLayoutManager(DatosActivity.this);
                    recycler.setLayoutManager(lManager);
                    adapterRepostaje = new ListaRepostajeAdapter(repostajes);
                    adapterRepostaje.setListener(DatosActivity.this); //Listener para el botón de factura
                    recycler.setAdapter(adapterRepostaje);

                    //Recuperamos datos de gastos
                    daoSession.getGastosDao();

                    gastoDao = daoSession.getGastosDao();

                    gastos = gastoDao.queryBuilder()
                            .where(GastosDao.Properties.IdVehiculo.eq(idBuscar), GastosDao.Properties.MesGastos.eq(mes)).build().list();

                    recycler1 = (RecyclerView) findViewById(recicladorMantenimientiTarjetaDatos);


                    lManager1 = new LinearLayoutManager(DatosActivity.this);
                    recycler1.setLayoutManager(lManager1);
                    adapterGastos = new ListaMantenimientoAdapter(gastos);
                    adapterGastos.setListener(DatosActivity.this); //Listener para el botón de factura
                    recycler1.setAdapter(adapterGastos);

                    //Recuperamos datos de impuestos
                    daoSession.getImpuestosDao();

                    impuestoDao = daoSession.getImpuestosDao();

                    impuestos = impuestoDao.queryBuilder()
                            .where(ImpuestosDao.Properties.IdVehiculo.eq(idBuscar), ImpuestosDao.Properties.MesImpuestos.eq(mes)).build().list();

                    recycler2 = (RecyclerView) findViewById(recicladorImpuestosTarjetaDatos);


                    lManager2 = new LinearLayoutManager(DatosActivity.this);
                    recycler2.setLayoutManager(lManager2);
                    adapterImpuesto = new ListaImpuestosAdapter(impuestos);
                    adapterImpuesto.setListener(DatosActivity.this); //Listener para el botón de factura
                    recycler2.setAdapter(adapterImpuesto);

                } else {

                    Toast.makeText(DatosActivity.this, R.string.sinVehiculos, Toast.LENGTH_LONG).show();
                }

            }
        });


        //Funcionalidad desplegar y contraer de los botones
        IVExpandirMantenimientoTarjetaDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recycler1 != null) { //Evitamos que si no hay datos la aplicación falle

                    if (recycler1.getVisibility() == View.GONE) {
                        recycler1.setVisibility(View.VISIBLE);
                        IVExpandirMantenimientoTarjetaDatos.setImageResource(R.drawable.recoger);
                    } else {
                        recycler1.setVisibility(View.GONE);
                        IVExpandirMantenimientoTarjetaDatos.setImageResource(R.drawable.desplegar);

                    }
                } else {

                    Toast.makeText(DatosActivity.this, R.string.sinBuscar, Toast.LENGTH_LONG).show();
                }
            }
        });


        IVExpandirRepostajeTarjetaDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recycler != null) { //Evitamos que si no hay datos la aplicación falle

                    if (recycler.getVisibility() == View.GONE) {
                        recycler.setVisibility(View.VISIBLE);
                        IVExpandirRepostajeTarjetaDatos.setImageResource(R.drawable.recoger);
                    } else {
                        recycler.setVisibility(View.GONE);
                        IVExpandirRepostajeTarjetaDatos.setImageResource(R.drawable.desplegar);
                    }
                } else {

                    Toast.makeText(DatosActivity.this, R.string.sinBuscar, Toast.LENGTH_LONG).show();
                }
            }
        });

        IVExpandirImpuestosTarjetaDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recycler2 != null) { //Evitamos que si no hay datos la aplicación falle

                    if (recycler2.getVisibility() == View.GONE) {
                        recycler2.setVisibility(View.VISIBLE);
                        IVExpandirImpuestosTarjetaDatos.setImageResource(R.drawable.recoger);
                    } else {
                        recycler2.setVisibility(View.GONE);
                        IVExpandirImpuestosTarjetaDatos.setImageResource(R.drawable.desplegar);

                    }
                } else {

                    Toast.makeText(DatosActivity.this, R.string.sinBuscar, Toast.LENGTH_LONG).show();
                }
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

    //Llamamos a la activity que contiene un imageview para mostrar la factura
    @Override
    public void examinarFactura(String uri) {

        if (uri.equals("")) {

            Toast.makeText(this, R.string.sinFactura, Toast.LENGTH_LONG).show();

        } else {
            Intent i = new Intent(this, FacturaActivity.class);
            i.putExtra("StringURI", uri); //Pasamos valor Uri
            startActivity(i);
        }
    }

}
