package com.example.doanqlsv.Model;

import java.util.List;

public class Diem {


    private double diemCK;
    private double diemGK;

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    private String maMon;
    public Diem(double diemCK, double diemGK, String emailGV, int maHK, String maSV,String maMon) {
        this.diemCK = diemCK;
        this.diemGK = diemGK;
        this.emailGV = emailGV;
        this.maHK = maHK;
        this.maSV = maSV;
        this.maMon=maMon;
    }

    public double getDiemCK() {
        return diemCK;
    }

    public void setDiemCK(double diemCK) {
        this.diemCK = diemCK;
    }

    public double getDiemGK() {
        return diemGK;
    }

    public void setDiemGK(double diemGK) {
        this.diemGK = diemGK;
    }

    public String getEmailGV() {
        return emailGV;
    }

    public void setEmailGV(String emailGV) {
        this.emailGV = emailGV;
    }

    public int getMaHK() {
        return maHK;
    }

    public void setMaHK(int maHK) {
        this.maHK = maHK;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    private String emailGV;
    private int maHK;
    private String maSV;

}
