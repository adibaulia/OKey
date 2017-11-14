package com.example.android.okey;

/**
 * Created by Adib Aulia R on 14/11/2017.
 */

public class TukangKunci {

    private double lat;
    private double lng;
    private String nama;
    private String no;


    public TukangKunci() {
    }

    public TukangKunci(double lat, double lng, String nama, String no) {
        this.lat = lat;
        this.lng = lng;
        this.nama = nama;
        this.no = no;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getNama() {
        return nama;
    }

    public String getNo() {
        return no;
    }
}
