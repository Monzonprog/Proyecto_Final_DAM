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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import java.util.Calendar;

import static android.media.MediaRecorder.VideoSource.CAMERA;

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

    private static final int GALLERY = 2;
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private String IMAGE_DIRECTORY = "/controlGasolina/";
    private Uri imageUri;
    private Uri yourUri;

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
                String uriUsuario = yourUri.toString();

                //TODO: Verificar que no haya campos vacios

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

                daoSession.insert(vehiculo);

                Toast.makeText(getActivity(), R.string.creacionVehiculoOk, Toast.LENGTH_LONG).show();
            }
        });

        añadirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camaraGaleria(v);
            }
        });

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
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY);
        }
        else {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_EXTERNAL_STORAGE);

        }
    }

    private void takePhotoFromCamera() {

        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){

            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, Calendar.getInstance()
                        .getTimeInMillis());
                //values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAMERA);
            }
            else {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE);

            }

        }
        else {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    takePhotoFromCamera();

                }
            }
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
}