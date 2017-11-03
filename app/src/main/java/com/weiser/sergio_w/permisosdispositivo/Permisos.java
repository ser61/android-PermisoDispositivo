package com.weiser.sergio_w.permisosdispositivo;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by Sergio_W on 03/11/2017.
 */

public class Permisos{

    public static final int CODIGO_SOLICITUD_PERMISO_BLUETOOTH = 1;
    public static final int CODIGO_SOLICITUD_HABILITAR_BLUETOOTH = 0;
    public static final int CODIGO_SOLICITUD_PERMISO_GPS = 2;
    public static final int CODIGO_SOLICITUD_PERMISO_CAMERA = 3;
    private Context context;
    private Activity activity;

    public Permisos(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void activarBluetooth() {
        solicitarPermisoBluetooth();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "Tu dispositivo no tien bluetooth...", Toast.LENGTH_SHORT).show();
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent habilitarBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(habilitarBluetooth, CODIGO_SOLICITUD_HABILITAR_BLUETOOTH);
        }
    }

    public void activarGps() {
        solicitarPermisoGps();
    }

    public void permisosCamara() {
        solicitarPermisoCamera();
    }

    //<editor-fold desc="CHECK STATUS PERMISSON">
    //CONSULTA SI APP TIENE HABILITADO EL PERMISO DEL BLUETOOTH
    public boolean checkStatusPermissonBluetooth() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkStatusPermissonGps() {
        int resultCoarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        int resultFine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (resultCoarse == PackageManager.PERMISSION_GRANTED && resultFine == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkStatusPermissonCamara() {
        int resultCamera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int resultRead = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int resultWrite = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int granted = PackageManager.PERMISSION_GRANTED;
        if (resultCamera == granted && resultRead == granted && resultWrite == granted) {
            return true;
        } else {
            return false;
        }
    }
    //</editor-fold>

    //<editor-fold desc="SOLICITAR PERMISOS">
    public void solicitarPermisoBluetooth() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH)) {
            Toast.makeText(context, "El permiso ya fue otorgado...", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH}, CODIGO_SOLICITUD_PERMISO_BLUETOOTH);
        }
    }

    public void solicitarPermisoGps() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                && ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(context, "El permiso ya fue otorgado...", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, CODIGO_SOLICITUD_PERMISO_GPS);
        }
    }

    public void solicitarPermisoCamera() {
        boolean camera = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA);
        boolean read = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean write = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (camera && read && write) {
            Toast.makeText(context, "El permisos ya fueron otorgados...", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, CODIGO_SOLICITUD_PERMISO_CAMERA);
        }
    }
    //</editor-fold>

}
