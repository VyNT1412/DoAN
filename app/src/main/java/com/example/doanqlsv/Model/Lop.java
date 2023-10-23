package com.example.doanqlsv.Model;

public class Lop {
    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public Lop(String maKhoa, String maLop) {
        this.maKhoa = maKhoa;
        this.maLop = maLop;
    }

    String maKhoa;
    String maLop;
}
