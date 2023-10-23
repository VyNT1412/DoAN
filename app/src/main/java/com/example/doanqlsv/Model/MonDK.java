package com.example.doanqlsv.Model;

import java.util.List;

public class MonDK {
    public String getEmailGV() {
        return emailGV;
    }

    public void setEmailGV(String emailGV) {
        this.emailGV = emailGV;
    }

    public String getHoGV() {
        return hoGV;
    }

    public void setHoGV(String hoGV) {
        this.hoGV = hoGV;
    }

    public String getTenGV() {
        return tenGV;
    }

    public void setTenGV(String tenGV) {
        this.tenGV = tenGV;
    }

    public String getGioDay() {
        return gioDay;
    }

    public void setGioDay(String gioDay) {
        this.gioDay = gioDay;
    }

    public String getGioKT() {
        return gioKT;
    }

    public void setGioKT(String gioKT) {
        this.gioKT = gioKT;
    }

    public int getMaHK() {
        return maHK;
    }

    public void setMaHK(int maHK) {
        this.maHK = maHK;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public List<String> getMaSV() {
        return maSV;
    }

    public void setMaSV(List<String> maSV) {
        this.maSV = maSV;
    }

    public String getNgayDay() {
        return ngayDay;
    }

    public void setNgayDay(String ngayDay) {
        this.ngayDay = ngayDay;
    }

    public MonDK(String emailGV, String hoGV, String tenGV, String gioDay, String gioKT, int maHK, String maKhoa, String maMon, List<String> maSV, String ngayDay) {
        this.emailGV = emailGV;
        this.hoGV = hoGV;
        this.tenGV = tenGV;
        this.gioDay = gioDay;
        this.gioKT = gioKT;
        this.maHK = maHK;
        this.maKhoa = maKhoa;
        this.maMon = maMon;
        this.maSV = maSV;
        this.ngayDay = ngayDay;
    }

    String emailGV;
    String hoGV;
    String tenGV;
    String gioDay;
    String gioKT;
    int maHK;
    String maKhoa;
    String maMon;
    List<String> maSV;
    String ngayDay;
}
