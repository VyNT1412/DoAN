package com.example.doanqlsv.Model;

public class Khoa {
    private String maKhoa;

    public String getMaTruongKhoa() {
        return maTruongKhoa;
    }

    public void setMaTruongKhoa(String maTruongKhoa) {
        this.maTruongKhoa = maTruongKhoa;
    }

    private String maTruongKhoa;

    public Khoa(String maKhoa, String truongKhoa) {
        this.maKhoa = maKhoa;
        this.maTruongKhoa = truongKhoa;
    }

    public Khoa() {

    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }



}
