package com.example.jorge.gasolinator.Fragments;


import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jorge.gasolinator.Adapters.ListaVehiculosAdapter;
import com.example.jorge.gasolinator.BBDD.db.DaoMaster;
import com.example.jorge.gasolinator.BBDD.db.DaoSession;
import com.example.jorge.gasolinator.BBDD.db.Vehiculos;
import com.example.jorge.gasolinator.BBDD.db.VehiculosDao;
import com.example.jorge.gasolinator.Interfaces.OpcionesTarjetaVehiculos;
import com.example.jorge.gasolinator.R;

import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaVehiculosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaVehiculosFragment extends Fragment implements OpcionesTarjetaVehiculos {

    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private ListaVehiculosAdapter adapter;
    private List<Vehiculos> vehiculos;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private VehiculosDao vehiculosDao;
    private Button cancelarEditar, cancelarBorrar, borrar, editar;
    private EditText marcaUsuario, modeloUsuario, apodoUsuario;


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

          }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_lista_vehiculos, container, false);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);


    }

    @Override
    public void onResume() {
        super.onResume();
        pintarListaVehiculos();

    }

    private void pintarListaVehiculos() { //Metodo para mostrar en pantalla los vehiculos de la BBDD

        //Recuperamos datos de la BBDD

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),"Vehiculos-db");
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        daoSession.getVehiculosDao();

        vehiculosDao = daoSession.getVehiculosDao();
        vehiculos = vehiculosDao.loadAll();

        //Llamamos al RecyclerView y al adapter para mostrar los datos

        recycler = (RecyclerView) getActivity().findViewById(R.id.recicladorFragmentListaVehiculos);


        lManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(lManager);
        adapter = new ListaVehiculosAdapter(vehiculos);
        adapter.setListener(ListaVehiculosFragment.this); //Listener para el botón de borrar y editar
        recycler.setAdapter(adapter);

    }

    @Override
    public void modificarVehiculo(final String id, String modelo, String marca, String apodo) { //Editamos datos y guardamos

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_editar);
        final Long lg = Long.parseLong(id);
        cancelarEditar = (Button)dialog.findViewById(R.id.buttonCancelarDialogEditar);
        editar = (Button)dialog.findViewById(R.id.buttonEditarDialogEditar);

        marcaUsuario = (EditText)dialog.findViewById(R.id.marcaEditTextDialog);
        modeloUsuario = (EditText)dialog.findViewById(R.id.modeloEditTextDialog);
        apodoUsuario = (EditText)dialog.findViewById(R.id.apodoEditTextDialog);
        marcaUsuario.setText(marca);
        modeloUsuario.setText(modelo);
        apodoUsuario.setText(apodo);


        dialog.show();

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehiculosDao vehiculosDao = daoSession.getVehiculosDao();
                Vehiculos vehiculo = new Vehiculos();
                vehiculo.setId(lg);
                vehiculo.setMarca(marcaUsuario.getText().toString());
                vehiculo.setModelo(modeloUsuario.getText().toString());
                vehiculo.setApodo(apodoUsuario.getText().toString());
                for (Vehiculos vehiculofor:vehiculos) {
                    if(Objects.equals(vehiculofor.getId(), lg)) {
                        vehiculo.setCombustible(vehiculofor.getCombustible());
                        vehiculo.setTipo(vehiculofor.getTipo());
                        vehiculo.setFoto_Uri(vehiculofor.getFoto_Uri());
                        break;
                    }
                }
                if (verificarDatos()) {

                    vehiculosDao.saveInTx(vehiculo);

                    Toast.makeText(getActivity(), getString(R.string.editarVehiculoOk), Toast.LENGTH_LONG).show();

                    dialog.cancel();

                    pintarListaVehiculos();

                }else{

                    Toast.makeText(getActivity(), R.string.datosIncompletos, Toast.LENGTH_LONG).show();
                }


            }
        });



        cancelarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }

    private boolean verificarDatos() { //Verificamos si los campos están rellenos

        boolean verificacion;

        if (marcaUsuario.getText().toString().isEmpty() || (modeloUsuario.getText().toString().isEmpty()) ||
                (apodoUsuario.getText().toString().isEmpty())) {
            verificacion = false;

        } else {

            verificacion = true;
        }

        return verificacion;
    }

    @Override
    public void eliminarVehiculo(final String id) { //Eliminamos el registro

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_borrar);
        cancelarBorrar = (Button)dialog.findViewById(R.id.buttonCancelarDialogBorrar);
        borrar = (Button)dialog.findViewById(R.id.buttonBorrarrDialogBorrar);
        final Long lg = Long.parseLong(id);

        dialog.show();

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehiculosDao vehiculosDao = daoSession.getVehiculosDao();

                vehiculosDao.deleteByKey(lg);

                Toast.makeText(getActivity(),getString(R.string.borrarVehiculoOk), Toast.LENGTH_LONG).show();

                dialog.cancel();

                pintarListaVehiculos();
            }
        });

        cancelarBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }

    //Método encargado de pintar los vehículos cuando cambiamos de pestaña
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getActivity() != null) {
            pintarListaVehiculos();
        }

    }
}
