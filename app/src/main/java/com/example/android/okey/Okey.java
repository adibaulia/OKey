package com.example.android.okey;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Adib Aulia R on 23/11/2017.
 */

public class Okey extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
       // FirebaseDatabase.getInstance().getReference().keepSynced(true);
    }
}
