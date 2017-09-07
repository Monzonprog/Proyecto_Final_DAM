package com.example.jorge.gasolinator.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorge.gasolinator.BBDD.db.DaoMaster;
import com.example.jorge.gasolinator.BBDD.db.DaoSession;
import com.example.jorge.gasolinator.BBDD.db.Gastos;
import com.example.jorge.gasolinator.BBDD.db.Impuestos;
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


public class ImpuestosActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int permsRequestCode = 200;
    private static final int GALLERY = 2;
    private ContentValues values;
    private String IMAGE_DIRECTORY = "/controlGasolina/";
    private Uri imageUri;
    private Uri yourUri = Uri.parse("");
    private Button fechaButtonImpuesto, añadirFotoImpuesto;
    private TextView fechaTVImpuesto;
    private String yearUsuario = "";
    private String monthUsuario = "";
    private String dayUsuario = "";
    private ImageView fotoImpuesto;
    private FloatingActionButton guardarImpuesto;
    private List<Vehiculos> vehiculos;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private VehiculosDao vehiculosDao;
    private Spinner vehiculoSpinnerImpuesto;
    private TextInputEditText conceptoETImpuesto, costeETImpuesto;
    private EditText descripcionImpuesto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impuestos);

        //Mostramos botón "Atrás" en la activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Declaración de elementos
        vehiculoSpinnerImpuesto = (Spinner) findViewById(R.id.vehiculoSpinnerImpuesto);
        fechaButtonImpuesto = (Button) findViewById(R.id.fechaButtonImpuesto);
        añadirFotoImpuesto = (Button) findViewById(R.id.añadirFotoImpuestos);
        fechaTVImpuesto = (TextView) findViewById(R.id.fechaTVImpuesto);
        fotoImpuesto = (ImageView) findViewById(R.id.fotoImpuestos);
        costeETImpuesto = (TextInputEditText) findViewById(R.id.costeETImpuesto);
        conceptoETImpuesto = (TextInputEditText) findViewById(R.id.conceptoETImpuesto);
        descripcionImpuesto = (EditText) findViewById(R.id.descripcionImpuesto);
        guardarImpuesto = (FloatingActionButton) findViewById(R.id.guardarImpuesto);

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
        vehiculoSpinnerImpuesto.setAdapter(dataAdapter);

        //Tomamos fecha
        fechaButtonImpuesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ImpuestosActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dayUsuario = String.valueOf(dayOfMonth);
                                monthUsuario = String.valueOf(monthOfYear+1);
                                yearUsuario = String.valueOf(year);

                                fechaTVImpuesto.setText(dayOfMonth + " - " + (monthOfYear + 1) +
                                        " - " + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        guardarImpuesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(vehiculos.size()==0){

                    Toast.makeText(ImpuestosActivity.this, R.string.sinVehiculos, Toast.LENGTH_LONG).show();

                }else {

                String idVehiculoUsuario = idVehiculoGuardar.get(vehiculoSpinnerImpuesto.getSelectedItemPosition());
                String coste = costeETImpuesto.getText().toString();
                String concepto = conceptoETImpuesto.getText().toString();
                String descripcion = descripcionImpuesto.getText().toString();
                String uriUsuario = verficarUri();

                //Abrimos bbdd y creamos registro de impuesto
                DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(ImpuestosActivity.this, "Vehiculos-db");
                Database db = helper.getWritableDb();
                daoSession = new DaoMaster(db).newSession();

                Impuestos impuestos = new Impuestos();
                impuestos.setIdVehiculo(idVehiculoUsuario);
                impuestos.setConcepto(concepto);
                impuestos.setCoste(coste);
                impuestos.setDescripcion(descripcion);
                impuestos.setDiaImpuestos(dayUsuario);
                impuestos.setMesImpuestos(monthUsuario);
                impuestos.setAñoImpuestos(yearUsuario);
                impuestos.setFoto_uri_impuesto(uriUsuario);



                    if (verificarDatos() && verificarFechas()) {

                        daoSession.insert(impuestos);

                        Toast.makeText(ImpuestosActivity.this, R.string.registroOk, Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(ImpuestosActivity.this, R.string.datosIncompletos, Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    private boolean verificarDatos() { //Verificamos si los campos están rellenos

        boolean verificacion;

        if (costeETImpuesto.getText().toString().isEmpty() || (conceptoETImpuesto.
                getText().toString().isEmpty()) || (descripcionImpuesto.
                getText().toString().isEmpty())) {
            verificacion = false;

        } else {

            verificacion = true;
        }

        return verificacion;
    }

    private boolean verificarFechas() { //Verificamos que el usuario ha introducido fecha

        boolean verificacion;

        if (dayUsuario.equals("") || monthUsuario.equals("") || yearUsuario.equals("")) {
            verificacion = false;

        } else {

            verificacion = true;
        }

        return verificacion;

    }

    //Verificamos Uri, si está vacia guardamos un string en blanco
    private String verficarUri() {

        if (yourUri.equals("")) {

            return "";
        } else {

            return yourUri.toString();
        }
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

    //Dialog para elegir de donde obtener la imagen
    public void camaraGaleria(View view) {

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Elige el origen de la fotografía");
        String[] pictureDialogItems = {
                "Elige una foto de la galería",
                "Haz una nueva fotografía"};
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

    //Abrimos y elegimos imagen de la galería
    public void choosePhotoFromGallary() {

        int storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY);

    }

    //Tomamos la foto de la cámara
    private void takePhotoFromCamera() {

        if (checkPermission()) {

            values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, Calendar.getInstance()
                    .getTimeInMillis());
            imageUri = this.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, CAMERA);


        }
    }

    //Resultado de la acción
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    fotoImpuesto.setImageBitmap(bitmap); //Pintamos la foto que hemos seleccionado
                    String path = saveImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == CAMERA) {
            if (resultCode == this.RESULT_OK) {
                try {
                    Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                            this.getContentResolver(), imageUri);
                    fotoImpuesto.setImageBitmap(thumbnail);
                    String imageUrl = getRealPathFromURI(imageUri);
                    String path = saveImage(thumbnail);
                    Log.e("EH", "Url es: " + imageUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

    //Guardamos imagen
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

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
            MediaScannerConnection.scanFile(this,
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
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //Permisos para el uso de cámara y galería
    private boolean checkPermission() {
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {


            ActivityCompat.requestPermissions(this, listPermissionsNeeded.
                    toArray(new String[listPermissionsNeeded.size()]), permsRequestCode);
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
                    boolean cameraAccepted = false;

                    if (grantResults.length > 1) {
                        cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    }


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE);
                    }

                    if (storageAccepted && cameraAccepted) {
                        values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, Calendar.getInstance()
                                .getTimeInMillis());
                        imageUri = this.getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, CAMERA);
                    } else {
                        Toast.makeText(this, getString(R.string.no_camera), Toast.LENGTH_LONG).show();
                    }
                }


                break;
        }
    }
}
