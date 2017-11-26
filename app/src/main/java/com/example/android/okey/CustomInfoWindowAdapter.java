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
    private final  ArrayList<TukangKunci> lokasi= new ArrayList<TukangKunci>();
    private Context mContext;
    private String no;

    public CustomInfoWindowAdapter(Context mContext, String no) {
        this.mContext = mContext;
        this.no=no;
        this.mWindow = LayoutInflater.from(mContext).inflate(R.layout.custom_info_window, null);
    }

    private void rendowWindowText(Marker marker, View v, ArrayList<TukangKunci> tukangKunci){
        String title = marker.getTitle();
        TextView tvTitle = (TextView) v.findViewById(R.id.title);

        if(!title.equals("")){
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = (TextView) v.findViewById(R.id.snippet);

        if(!snippet.equals("")){
            tvSnippet.setText(snippet);
        }

        Toast.makeText(mContext, no, Toast.LENGTH_SHORT).show();

        Button btCall = (Button) v.findViewById(R.id.call);
        btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "TEST", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow,lokasi);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow,lokasi);
        return mWindow;
    }
}
