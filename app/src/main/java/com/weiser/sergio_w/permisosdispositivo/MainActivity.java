package com.weiser.sergio_w.permisosdispositivo;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CODIGO_SOLICITUD_PERMISO = 1;
    private static final int CODIGO_SOLICITUD_HABILITAR_BLUETOOTH = 0;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        activity = this;
    }

    public void activarBluetooth(View view) {
        solicitarPermiso();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "Tu dispositivo no tien bluetooth...", Toast.LENGTH_SHORT).show();
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent habilitarBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(habilitarBluetooth, CODIGO_SOLICITUD_HABILITAR_BLUETOOTH);
        }
    }

    //CONSULTA SI APP TIENE HABILITADO EL PERMISO DEL BLUETOOTH
    public boolean checkStatusPermisson() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void solicitarPermiso() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH)) {
            Toast.makeText(context, "El permiso ya fue otorgado...", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH}, CODIGO_SOLICITUD_PERMISO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODIGO_SOLICITUD_PERMISO:
                if (checkStatusPermisson()) {
                    Toast.makeText(context, "Ya esta activo el permiso para el Bluetooth", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "El permiso de bluetooth no esta activo...", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
