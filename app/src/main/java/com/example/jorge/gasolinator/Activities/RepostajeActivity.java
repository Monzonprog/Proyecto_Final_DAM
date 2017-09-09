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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorge.gasolinator.BBDD.db.DaoMaster;
import com.example.jorge.gasolinator.BBDD.db.DaoSession;
import com.example.jorge.gasolinator.BBDD.db.Repostaje;
import com.example.jorge.gasolinator.BBDD.db.Vehiculos;
import com.example.jorge.gasolinator.BBDD.db.VehiculosDao;
import com.example.jorge.gasolinator.R;

import org.greenrobot.greendao.database.Database;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.media.MediaRecorder.VideoSource.CAMERA;


public class RepostajeActivity extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int permsRequestCode = 200;
    private static final int GALLERY = 2;
    private ContentValues values;
    private String IMAGE_DIRECTORY = "/controlGasolina/";
    private Uri imageUri;
    private Uri yourUri = Uri.parse("");
    private Spinner vehiculosSpinnerRepostaje;
    private RadioButton totalRBRepostaje;
    private TextInputEditText kmActualesETRepostaje, costeETRepostaje, litrosETRespostaje;
    private Button fechaRepostaje;
    private TextView fechaTVRepostaje;
    private String yearUsuario = "";
    private String monthUsuario = "";
    private String dayUsuario = "";
    private ImageView fotoRepostaje;
    private FloatingActionButton guardarRespostaje;
    private List<Vehiculos> vehiculos;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private VehiculosDao vehiculosDao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repostaje);

        //Mostramos botón "Atrás" en la activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Declaramos los elementos
        fotoRepostaje = (ImageView) findViewById(R.id.fotoRepostaje);
        vehiculosSpinnerRepostaje = (Spinner) this.findViewById(R.id.vehiculoSpinnerRepostaje);
        guardarRespostaje = (FloatingActionButton)this.findViewById(R.id.guardarRespostaje);
        totalRBRepostaje = (RadioButton)this.findViewById(R.id.totalRBRepostaje);
        kmActualesETRepostaje = (TextInputEditText)this.findViewById(R.id.kmActualesETRepostaje);
        costeETRepostaje = (TextInputEditText)this.findViewById(R.id.costeETRepostaje);
        litrosETRespostaje = (TextInputEditText)this.findViewById(R.id.litrosETRespostaje);
        fechaRepostaje = (Button)this.findViewById(R.id.fechaButtonRepostaje);
        fechaTVRepostaje = (TextView)this.findViewById(R.id.fechaTVRepostaje);

        //Recuperamos datos de los vehiculos creados
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "Vehiculos-db");
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        daoSession.getVehiculosDao();

        vehiculosDao = daoSession.getVehiculosDao();
        vehiculos = vehiculosDao.loadAll();

        //Listas para pintar la lista de vehiculos y después guardar su referencia
        List<String> coches = new ArrayList<>();
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
        vehiculosSpinnerRepostaje.setAdapter(dataAdapter);

        //Tomamos fecha
        fechaRepostaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RepostajeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dayUsuario = String.valueOf(dayOfMonth);
                                monthUsuario = String.valueOf(monthOfYear + 1);
                                yearUsuario = String.valueOf(year);

                                fechaTVRepostaje.setText(dayOfMonth + " - " + (monthOfYear + 1) +
                                        " - " + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        guardarRespostaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(vehiculos.size()==0){

                    Toast.makeText(RepostajeActivity.this, R.string.sinVehiculos, Toast.LENGTH_LONG).show();

                }else {

                    String idVehiculoUsuario = idVehiculoGuardar.get(vehiculosSpinnerRepostaje.getSelectedItemPosition());
                    String llenadoUsuario = checkTipoLlenado();
                    String kmActuales = kmActualesETRepostaje.getText().toString();
                    String costeRespostaje = costeETRepostaje.getText().toString();
                    String litrosRespostaje = litrosETRespostaje.getText().toString();

                    //Tomamos los valores de litros y coste para sacar la media, pintamos solo 3 decimales
                    DecimalFormat decimales = new DecimalFormat("0.000");
                    double aux = Double.parseDouble(costeRespostaje)/Double.parseDouble(litrosRespostaje);
                    String precioLitrosRespostaje =String.valueOf(decimales.format(aux));

                    String uriUsuario = verficarUri();

                    //Abrimos bbdd y creamos registro de repostaje
                    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(RepostajeActivity.this, "Vehiculos-db");
                    Database db = helper.getWritableDb();
                    daoSession = new DaoMaster(db).newSession();

                    Repostaje repostaje = new Repostaje();
                    repostaje.setIdVehiculo(idVehiculoUsuario);
                    repostaje.setTipoLlenado(llenadoUsuario);
                    repostaje.setKmActuales(kmActuales);
                    repostaje.setCosteRepostaje(costeRespostaje);
                    repostaje.setLitrosRepostaje(litrosRespostaje);
                    repostaje.setPrecioLitroRepostaje(precioLitrosRespostaje);
                    repostaje.setDiaRepostaje(dayUsuario);
                    repostaje.setMesRepostaje(monthUsuario);
                    repostaje.setAñoRepostaje(yearUsuario);
                    repostaje.setFoto_Uri(uriUsuario);

                    if (verificarDatos() && verificarFechas()) {

                        daoSession.insert(repostaje);

                        Toast.makeText(RepostajeActivity.this, R.string.registroOk, Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(RepostajeActivity.this, R.string.datosIncompletos, Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }

    private boolean verificarFechas() { //Verificamos si los campos están rellenos


        boolean verificacion;

        if (dayUsuario.equals("") || monthUsuario.equals("") || yearUsuario.equals("")) {
            verificacion = false;

        } else {

            verificacion = true;
        }

        return verificacion;

    }



    private String checkTipoLlenado() { //Tomamos el dato delRadioButton

        String tipo;

        if(totalRBRepostaje.isChecked()){

             tipo = "Total";
        }else{

            tipo = "Parcial";
        }

        return tipo;
    }


    private boolean verificarDatos() { //Verificamos si los campos están rellenos

        boolean verificacion;

        if (kmActualesETRepostaje.getText().toString().isEmpty() || (costeETRepostaje.getText().toString().isEmpty()) ||
                (litrosETRespostaje.getText().toString().isEmpty())) {
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



            values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, Calendar.getInstance()
                    .getTimeInMillis());
            imageUri = this.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, CAMERA);





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

                    fotoRepostaje.setImageBitmap(bitmap); //Pintamos la foto que hemos seleccionado
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
                    fotoRepostaje.setImageBitmap(thumbnail);
                    String imageUrl = getRealPathFromURI(imageUri);
                    String path = saveImage(thumbnail);
                    Log.e("EH", "Url es: " + imageUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

    //Resultado de la acción
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

