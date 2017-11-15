package com.example.android.okey;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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
    }

    public void buttonSave(View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        TukangKunci tukang = new TukangKunci();

        tukang.setId(myRef.push().getKey());
        tukang.setLat(latitude.getText().toString().trim());
        tukang.setLng(longitude.getText().toString().trim());
        tukang.setNama(namaTukang.getText().toString().trim());
        tukang.setNo(nomorHp.getText().toString().trim());
        tukang.setSpesifikasi(spesifikasi.getText().toString());



        namaTukang.setText("");
        latitude.setText("");
        longitude.setText("");
        nomorHp.setText("");
        spesifikasi.setText("");




        myRef.child(myRef.push().getKey()).setValue(tukang);
    }
}
