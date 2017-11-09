package com.example.android.okey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

public class Splash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1500;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this,MapsMain.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
