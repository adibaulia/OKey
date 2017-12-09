package com.example.android.okey;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by Adib Aulia R on 24/11/2017.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;
    private String no;
    private Location current;

    public CustomInfoWindowAdapter(Context mContext, String no, Location current) {
        this.mContext = mContext;
        this.current = current;
        this.no=no;
        this.mWindow = LayoutInflater.from(mContext).inflate(R.layout.custom_info_window, null);
    }

    private void rendowWindowText(Marker marker, View v){

    //    float[] results = new float[1];
     //   Location.distanceBetween(current.getLatitude(), current.getLongitude(), marker.getPosition().latitude, marker.getPosition().longitude, results);
        String title = marker.getTitle();
        TextView tvTitle = v.findViewById(R.id.title);
        TextView tvAlamat = v.findViewById(R.id.alamat);
        TextView tvJarak = v.findViewById(R.id.jarak);
    //    tvJarak.setText(String.valueOf(results[1]));


        TukangKunci tukang = (TukangKunci)marker.getTag();
        tvAlamat.setText(tukang.getAlamat());

        if(!title.equals("")){
            tvTitle.setText(title);
          //  tvTitle1.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = v.findViewById(R.id.snippet);

        if(!snippet.equals("")){
            tvSnippet.setText(snippet);
        }

    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}
