package com.example.jorge.gasolinator.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.jorge.gasolinator.Class.TouchImageView;
import com.example.jorge.gasolinator.R;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class FacturaActivity extends AppCompatActivity {

    private TouchImageView facturaIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        facturaIV = (TouchImageView) findViewById(R.id.facturaIV);

        Bundle extras = getIntent().getExtras();
        String uri = extras.getString("StringURI");

        Picasso.with(this).load(uri)
                .into(facturaIV, new Callback() {

            @Override
            public void onSuccess() {

                facturaIV.resetZoom();
                facturaIV.setZoom(facturaIV.getCurrentZoom()-0.5f);
                facturaIV.resetZoom();
                facturaIV.setZoom(facturaIV.getCurrentZoom());
            }

            @Override
            public void onError() {

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
