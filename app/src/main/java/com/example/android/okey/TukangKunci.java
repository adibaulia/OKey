package com.example.android.okey;

/**
 * Created by Adib Aulia R on 14/11/2017.
 */


import com.google.firebase.database.IgnoreExtraProperties;

public class TukangKunci {

    private String id;
    private String lat;
    private String lng;
    private String nama;
    private String no;
    private String Spesifikasi;

    public String getSpesifikasi() {
        return Spesifikasi;
    }

    public void setSpesifikasi(String spesifikasi) {
        Spesifikasi = spesifikasi;
    }

    public TukangKunci() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}

