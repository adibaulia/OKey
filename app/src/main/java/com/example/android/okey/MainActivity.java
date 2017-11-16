package com.example.android.okey;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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


public class MainActivity extends AppCompatActivity implements LocationListener{


    EditText namaTukang;
    EditText latitude;
    EditText longitude;
    EditText nomorHp;
    EditText spesifikasi;
    EditText alamat;
    Button getLoc;
    LocationManager locationManager;


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


        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                // get the last know location from your location manager.
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, MainActivity.this );
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                // now get the lat/lon from the location and do something with it.
                ProgressDialog progress = new ProgressDialog(MainActivity.this);
                progress.setTitle("Loading");
                progress.setMessage("Wait while loading...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();
                if(location!=null){

                    latitude.setText(String.valueOf(location.getLatitude()));
                    longitude.setText(String.valueOf(location.getLongitude()));
                    Geocoder gCoder = new Geocoder(MainActivity.this);
                    List<Address> addresses = null;
                    try {
                        addresses = gCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        alamat.setText(String.valueOf(addresses.get(0).getAddressLine(0)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    progress.dismiss();
                }else{
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, MainActivity.this );
                    Toast.makeText(MainActivity.this, "NULL", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void buttonSave(View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        TukangKunci tukang = new TukangKunci();

        String nama=namaTukang.getText().toString();
        String lat=latitude.getText().toString().trim();
        String lng=longitude.getText().toString().trim();
        String no=nomorHp.getText().toString().trim();
        String spek=spesifikasi.getText().toString();
        String alamat1=alamat.getText().toString();

        if(Objects.equals(alamat1, "") || Objects.equals(nama, "") || Objects.equals(lat, "") || Objects.equals(lng, "") || Objects.equals(no, "") || (Objects.equals(spek, ""))){
            namaTukang.setText("");
            latitude.setText("");
            longitude.setText("");
            nomorHp.setText("");
            spesifikasi.setText("");
            alamat.setText("");
            Toast.makeText(this, "ISI SEMUA FORM YANG ADA", Toast.LENGTH_SHORT).show();
        }else {
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

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(MainActivity.this);

    }
    @Override
    public void onLocationChanged(Location location) {
        locationManager.removeUpdates(MainActivity.this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void buttonMaps(View v){
        Intent intent = new Intent(this, MapsMain.class);
        startActivity(intent);
    }
}
