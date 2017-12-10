package com.example.android.okey;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Adib Aulia R on 24/11/2017.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private View mWindow;
    private Context mContext;
    private String no;
    private LatLng current;

    public CustomInfoWindowAdapter(Context mContext, String no, LatLng current) {
        this.mContext = mContext;
        this.no=no;
        this.current=current;
        this.mWindow = LayoutInflater.from(mContext).inflate(R.layout.custom_info_window, null);
    }


    private void rendowWindowText(Marker marker, View v){


        Location loc1 = new Location("");
        loc1.setLatitude(current.latitude);
        loc1.setLongitude(current.longitude);
        Location loc2 = new Location("");
        loc2.setLatitude(marker.getPosition().latitude);
        loc2.setLongitude(marker.getPosition().longitude);
        float distanceInMeters = loc1.distanceTo(loc2);

        String title = marker.getTitle();
        TextView tvTitle = v.findViewById(R.id.title);
        TextView tvAlamat = v.findViewById(R.id.alamat);
        TextView tvJarak = v.findViewById(R.id.jarak);
        tvJarak.setText(" ("+String.valueOf((distanceInMeters)/1000).replace("." , ",")+" km)");

        TukangKunci tukang = (TukangKunci)marker.getTag();
        tvAlamat.setText(tukang.getAlamat());

        if(!title.equals("")){
            tvTitle.setText(" "+title);
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
