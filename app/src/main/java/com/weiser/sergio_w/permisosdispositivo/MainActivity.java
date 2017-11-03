package com.weiser.sergio_w.permisosdispositivo;

import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Permisos permisos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.permisos = new Permisos(getApplicationContext(), this);
    }

    public void activarBluetooth(View view) {
        permisos.activarBluetooth();
    }

    public void activarGps(View view) {
        permisos.activarGps();
    }

    public void permisosCamera(View view) {
        permisos.permisosCamara();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Permisos.CODIGO_SOLICITUD_PERMISO_BLUETOOTH:
                if (permisos.checkStatusPermissonBluetooth()) {
                    Toast.makeText(this, "Ya esta activo el permiso para el Bluetooth", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "El permiso de bluetooth no esta activo...", Toast.LENGTH_SHORT).show();
                }
                break;
            case Permisos.CODIGO_SOLICITUD_PERMISO_GPS:
                if (permisos.checkStatusPermissonGps()) {
                    Toast.makeText(this, "Ya esta activo el permiso para Gps", Toast.LENGTH_SHORT).show();
                    LocationManager mlocManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
                    final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if (!gpsEnabled) {
                        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(settingsIntent);
                    }
                } else {
                    Toast.makeText(this, "El permiso Gps no esta activo...", Toast.LENGTH_SHORT).show();
                }
                break;
            case Permisos.CODIGO_SOLICITUD_PERMISO_CAMERA:
                Toast.makeText(this, "El permisos ya fueron otorgados...", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
