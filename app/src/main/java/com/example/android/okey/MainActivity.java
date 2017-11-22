package com.example.android.okey;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {


    private final static int ALL_PERMISSIONS_RESULT = 101;
    EditText namaTukang;
    EditText latitude;
    EditText longitude;
    EditText nomorHp;
    EditText spesifikasi;
    EditText alamat;
    Button getLoc;
    LocationTrack locationTrack;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLoc = findViewById(R.id.buttonLocation);
        namaTukang = findViewById(R.id.namaTukang);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        nomorHp = findViewById(R.id.nomorhp);
        spesifikasi = findViewById(R.id.spesifikasi);
        alamat = findViewById(R.id.alamat);
        namaTukang.setText("");
        latitude.setText("");
        longitude.setText("");
        nomorHp.setText("");
        alamat.setText("");
        spesifikasi.setText("");

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationTrack = new LocationTrack(MainActivity.this);
                ProgressDialog progress = new ProgressDialog(view.getContext());
                progress.setTitle("Loading");
                progress.setMessage("Mengambil data dari database");
                progress.setCancelable(true);
                progress.show();

                if (locationTrack.canGetLocation()) {
                    latitude.setText(String.valueOf(locationTrack.getLatitude()));
                    longitude.setText(String.valueOf(locationTrack.getLongitude()));
                    Geocoder gCoder = new Geocoder(MainActivity.this);
                    List<Address> addresses = null;
                    try {
                        addresses = gCoder.getFromLocation(locationTrack.getLatitude(), locationTrack.getLongitude(), 1);
                        alamat.setText(String.valueOf(addresses.get(0).getAddressLine(0)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    locationTrack.showSettingsAlert();
                }

                if(latitude.getText()!=null || !Objects.equals(latitude.getText().toString(), "")){
                    progress.dismiss();
                }

            }
        });

    }


    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(Object permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission((String) permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]),
                                                        ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }


    public void buttonSave(View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        TukangKunci tukang = new TukangKunci();

        String nama = namaTukang.getText().toString();
        String lat = latitude.getText().toString().trim();
        String lng = longitude.getText().toString().trim();
        String no = nomorHp.getText().toString().trim();
        String spek = spesifikasi.getText().toString();
        String alamat1 = alamat.getText().toString();

        if (Objects.equals(alamat1, "") || Objects.equals(nama, "") || Objects.equals(lat, "") || Objects.equals(lng, "") || Objects.equals(no, "") || (Objects.equals(spek, ""))) {
            namaTukang.setText("");
            latitude.setText("");
            longitude.setText("");
            nomorHp.setText("");
            spesifikasi.setText("");
            alamat.setText("");
            Toast.makeText(this, "ISI SEMUA FORM YANG ADA!", Toast.LENGTH_SHORT).show();
        } else {
            tukang.setId(myRef.push().getKey());
            tukang.setLat(lat);
            tukang.setLng(lng);
            tukang.setNama(nama);
            tukang.setNo(no);
            tukang.setSpesifikasi(spek);
            tukang.setAlamat(alamat1);
            if (!myRef.child(myRef.push().getKey()).setValue(tukang).isSuccessful()) {
                Toast.makeText(this, "Berhasil Menambahkan Tukang Kunci " + nama, Toast.LENGTH_SHORT).show();
                namaTukang.setText("");
                latitude.setText("");
                longitude.setText("");
                nomorHp.setText("");
                alamat.setText("");
                spesifikasi.setText("");
            }
        }
    }

    public void getAddress(View v) {
        Geocoder gCoder = new Geocoder(MainActivity.this);
        List<Address> addresses = null;
        try {
            addresses = gCoder.getFromLocation(Double.parseDouble(latitude.getText().toString().trim()),
                    Double.parseDouble(longitude.getText().toString().trim()), 1);
            alamat.setText(String.valueOf(addresses.get(0).getAddressLine(0)));
        } catch (IOException e) {
            Toast.makeText(this, "Alamat tidak tersedia", Toast.LENGTH_SHORT).show();
        }
    }


    public void buttonMaps(View v) {
        Intent intent = new Intent(this, MapsMain.class);
        startActivity(intent);
    }
}
