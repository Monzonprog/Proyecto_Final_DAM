package com.example.jorge.gasolinator.Fragments;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jorge.gasolinator.BBDD.db.DaoMaster;
import com.example.jorge.gasolinator.BBDD.db.DaoSession;
import com.example.jorge.gasolinator.BBDD.db.Vehiculos;
import com.example.jorge.gasolinator.BBDD.db.VehiculosDao;
import com.example.jorge.gasolinator.R;

import org.greenrobot.greendao.database.Database;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.media.MediaRecorder.VideoSource.CAMERA;
import static com.example.jorge.gasolinator.R.id.view;

/**
 * Created by jorge on 10/04/17.
 */

public class VehiculosFragment extends Fragment  {

    private TextInputEditText marca;
    private TextInputEditText modelo;
    private TextInputEditText apodo;
    private FloatingActionButton crearVehiculo;
    private Spinner tipo;
    private Spinner combustible;
    private Button añadirFoto;
    private ImageView fotoAñadida;

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int permsRequestCode = 200;

    private static final int GALLERY = 2;
    private String IMAGE_DIRECTORY = "/controlGasolina/";
    private Uri imageUri;
    private Uri yourUri = null;

    private ContentValues values;

    private DaoSession daoSession;



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

        View view = inflater.inflate(R.layout.nuevo_vehiculo_fragment, container, false);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        marca = (TextInputEditText) getActivity().findViewById(R.id.marcaVehiculoAñadirVehiculos);
        modelo = (TextInputEditText) getActivity().findViewById(R.id.modeloVehiculoAñadirVehiculos);
        apodo = (TextInputEditText) getActivity().findViewById(R.id.apodoVehiculoAñadirVehiculos);
        tipo = (Spinner) getActivity().findViewById(R.id.tipoVehiculoAñadirVehiculos);
        combustible = (Spinner) getActivity().findViewById(R.id.combustibleVehiculoAñadirVehiculos);
        crearVehiculo = (FloatingActionButton) getActivity().findViewById(R.id.crearVehiculoAñadirVehiculos);
        fotoAñadida = (ImageView) getActivity().findViewById(R.id.fotoAñadidaAñadirVehiculos);
        añadirFoto = (Button)getActivity().findViewById(R.id.añadirFotoAñadirVehiculos);


        crearVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String marcaUsuario = marca.getText().toString();
                String modeloUsuario = modelo.getText().toString();
                String apodoUsuario = apodo.getText().toString();
                String tipoUsuario = tipo.getSelectedItem().toString();
                String combustibleUsuario = combustible.getSelectedItem().toString();
                String uriUsuario = verficarUri();


                DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),"Vehiculos-db"); //The users-db here is the name of our database.
                Database db = helper.getWritableDb();

                daoSession = new DaoMaster(db).newSession();

                Vehiculos vehiculo = new Vehiculos();
                vehiculo.setMarca(marcaUsuario);
                vehiculo.setModelo(modeloUsuario);
                vehiculo.setApodo(apodoUsuario);
                vehiculo.setTipo(tipoUsuario);
                vehiculo.setCombustible(combustibleUsuario);
                vehiculo.setFoto_Uri(uriUsuario);

                if(verificarDatos()){

                daoSession.insert(vehiculo);

                Toast.makeText(getActivity(), R.string.creacionVehiculoOk, Toast.LENGTH_LONG).show();

                }else{

                    Toast.makeText(getActivity(), R.string.datosIncompletos, Toast.LENGTH_LONG).show();
                }
            }
        });

        añadirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camaraGaleria(v);
            }
        });

    }

    private String verficarUri() {

        if(yourUri.toString() == null){

            return " ";
        }else{

            return yourUri.toString();
        }
    }

    private boolean verificarDatos() { //Verificamos si los campos están rellenos

        boolean verificacion;

        if(marca.getText().toString().isEmpty() || (modelo.getText().toString().isEmpty())||
                (apodo.getText().toString().isEmpty())){
            verificacion = false;

        }else{

            verificacion = true;
        }

        return verificacion;
    }

    public void camaraGaleria(View view){

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Elige el origen de la fotografía");
        String[] pictureDialogItems = {
                "Elige una foto de la galería",
                "Haz una nueva fotografía" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {

        int storagePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY);




    }

    private void takePhotoFromCamera() {

        if(checkPermission()){

            values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, Calendar.getInstance()
                    .getTimeInMillis());
            imageUri = getActivity().getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, CAMERA);


        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);

                    fotoAñadida.setImageBitmap(bitmap); //Pintamos la foto que hemos seleccionado
                    String path = saveImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == CAMERA) {
            if (resultCode == getActivity().RESULT_OK) {
                try {
                    Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                            getActivity().getContentResolver(), imageUri);
                    fotoAñadida.setImageBitmap(thumbnail);
                    String imageUrl = getRealPathFromURI(imageUri);
                    Log.e("EH", "Url es: "+ imageUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }


    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            yourUri = Uri.fromFile(f); //USAR PARA GUARDAR EN BBDD

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return "";

    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private boolean checkPermission() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA);
        int storagePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {


            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.
                    toArray(new String[listPermissionsNeeded.size()]),permsRequestCode);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {

                        }

                    }
                }


                break;
        }
    }





}


