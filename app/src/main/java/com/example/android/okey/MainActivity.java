package com.example.android.okey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {


    EditText namaTukang;
    EditText latitude;
    EditText longitude;
    EditText nomorHp;
    EditText spesifikasi;
    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        save = findViewById(R.id.buttonSave);
        namaTukang = findViewById(R.id.namaTukang);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        nomorHp = findViewById(R.id.nomorhp);
        spesifikasi = findViewById(R.id.spesifikasi);
        namaTukang.setText("");
        latitude.setText("");
        longitude.setText("");
        nomorHp.setText("");
        spesifikasi.setText("");
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


        if(Objects.equals(nama, "") || Objects.equals(lat, "") || Objects.equals(lng, "") || Objects.equals(no, "") || (Objects.equals(spek, ""))){
            namaTukang.setText("");
            latitude.setText("");
            longitude.setText("");
            nomorHp.setText("");
            spesifikasi.setText("");
            Toast.makeText(this, "ISI SEMUA FORM YANG ADA", Toast.LENGTH_SHORT).show();
        }else {
            tukang.setId(myRef.push().getKey());
            tukang.setLat(lat);
            tukang.setLng(lng);
            tukang.setNama(nama);
            tukang.setNo(no);
            tukang.setSpesifikasi(spek);
            if (!myRef.child(myRef.push().getKey()).setValue(tukang).isSuccessful()) {
                Toast.makeText(this, "Berhasil Menambahkan Tukang Kunci " + nama, Toast.LENGTH_SHORT).show();
                namaTukang.setText("");
                latitude.setText("");
                longitude.setText("");
                nomorHp.setText("");
                spesifikasi.setText("");
            }
        }
    }
    public void buttonMaps(View v){
        Intent intent = new Intent(this, MapsMain.class);
        startActivity(intent);
    }
}
