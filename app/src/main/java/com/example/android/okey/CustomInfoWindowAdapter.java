package com.example.android.okey;

import android.content.Context;
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

    public CustomInfoWindowAdapter(Context mContext, String no) {
        this.mContext = mContext;
        this.no=no;
        this.mWindow = LayoutInflater.from(mContext).inflate(R.layout.custom_info_window, null);
    }

    private void rendowWindowText(Marker marker, View v){
        String title = marker.getTitle();
        TextView tvTitle = v.findViewById(R.id.title);
        TextView tvTitle1 = v.findViewById(R.id.title1);

        if(!title.equals("")){
            tvTitle.setText(title);
            tvTitle1.setText(title);
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
