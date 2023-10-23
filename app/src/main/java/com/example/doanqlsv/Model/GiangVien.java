package com.example.doanqlsv.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class GiangVien implements Parcelable {
    protected GiangVien(Parcel in) {
        emailGV = in.readString();
        hoGV = in.readString();
        tenGV = in.readString();
        maKhoa = in.readString();
        passwordGV = in.readString();
        gioiTinhGV = in.readString();
        salaryGV = in.readDouble();
        hocVi = in.readString();
        chucDanh = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(emailGV);
        dest.writeString(hoGV);
        dest.writeString(tenGV);
        dest.writeString(maKhoa);
        dest.writeString(passwordGV);
        dest.writeString(gioiTinhGV);
        dest.writeDouble(salaryGV);
        dest.writeString(hocVi);
        dest.writeString(chucDanh);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GiangVien> CREATOR = new Creator<GiangVien>() {
        @Override
        public GiangVien createFromParcel(Parcel in) {
            return new GiangVien(in);
        }

        @Override
        public GiangVien[] newArray(int size) {
            return new GiangVien[size];
        }
    };

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

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getPasswordGV() {
        return passwordGV;
    }

    public void setPasswordGV(String passwordGV) {
        this.passwordGV = passwordGV;
    }



    public double getSalaryGV() {
        return salaryGV;
    }

    public void setSalaryGV(double salaryGV) {
        this.salaryGV = salaryGV;
    }

    public String getHocVi() {
        return hocVi;
    }

    public void setHocVi(String hocVi) {
        this.hocVi = hocVi;
    }

    public String getChucDanh() {
        return chucDanh;
    }

    public void setChucDanh(String chucDanh) {
        this.chucDanh = chucDanh;
    }

    private String emailGV;
    private String hoGV;
    private String tenGV;
    private String maKhoa;
    private String passwordGV;

    public String getGioiTinhGV() {
        return gioiTinhGV;
    }

    public void setGioiTinhGV(String gioiTinhGV) {
        this.gioiTinhGV = gioiTinhGV;
    }

    private String gioiTinhGV;

    public GiangVien(String emailGV, String hoGV, String tenGV, String maKhoa, String passwordGV, String gioiTinhGV, double salaryGV, String hocVi, String chucDanh) {
        this.emailGV = emailGV;
        this.hoGV = hoGV;
        this.tenGV = tenGV;
        this.maKhoa = maKhoa;
        this.passwordGV = passwordGV;
        this.gioiTinhGV = gioiTinhGV;
        this.salaryGV = salaryGV;
        this.hocVi = hocVi;
        this.chucDanh = chucDanh;
    }

    private double salaryGV;
    private String hocVi;
    private String chucDanh;
    public GiangVien()
    {}
}
