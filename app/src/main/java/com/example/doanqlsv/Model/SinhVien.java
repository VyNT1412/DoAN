package com.example.doanqlsv.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class SinhVien implements Parcelable {


    protected SinhVien(Parcel in) {
        maSV = in.readString();
        hoSV = in.readString();
        maKhoa = in.readString();
        tenSV = in.readString();
        passwordSV = in.readString();
        gioitinhSV = in.readString();
        emailSV = in.readString();
        maLop = in.readString();
        ngaysinhSV = in.readString();
        quequanSV = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maSV);
        dest.writeString(hoSV);
        dest.writeString(maKhoa);
        dest.writeString(tenSV);
        dest.writeString(passwordSV);
        dest.writeString(gioitinhSV);
        dest.writeString(emailSV);
        dest.writeString(maLop);
        dest.writeString(ngaysinhSV);
        dest.writeString(quequanSV);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SinhVien> CREATOR = new Creator<SinhVien>() {
        @Override
        public SinhVien createFromParcel(Parcel in) {
            return new SinhVien(in);
        }

        @Override
        public SinhVien[] newArray(int size) {
            return new SinhVien[size];
        }
    };

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoSV() {
        return hoSV;
    }

    public void setHoSV(String hoSV) {
        this.hoSV = hoSV;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }

    public String getPasswordSV() {
        return passwordSV;
    }

    public void setPasswordSV(String passwordSV) {
        this.passwordSV = passwordSV;
    }

    public String getGioitinhSV() {
        return gioitinhSV;
    }

    public void setGioitinhSV(String gioitinhSV) {
        this.gioitinhSV = gioitinhSV;
    }

    public String getEmailSV() {
        return emailSV;
    }

    public void setEmailSV(String emailSV) {
        this.emailSV = emailSV;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getNgaysinhSV() {
        return ngaysinhSV;
    }

    public void setNgaysinhSV(String ngaysinhSV) {
        this.ngaysinhSV = ngaysinhSV;
    }

    public String getQuequanSV() {
        return quequanSV;
    }

    public void setQuequanSV(String quequanSV) {
        this.quequanSV = quequanSV;
    }

    public SinhVien(String maSV, String hoSV, String maKhoa, String tenSV, String passwordSV, String gioitinhSV, String emailSV, String maLop, String ngaysinhSV, String quequanSV) {
        this.maSV = maSV;
        this.hoSV = hoSV;
        this.maKhoa = maKhoa;
        this.tenSV = tenSV;
        this.passwordSV = passwordSV;
        this.gioitinhSV = gioitinhSV;
        this.emailSV = emailSV;
        this.maLop = maLop;
        this.ngaysinhSV = ngaysinhSV;
        this.quequanSV = quequanSV;
    }

    private String maSV;
    private String hoSV;
    private String maKhoa;
    private String tenSV;
    private String passwordSV;
    private String gioitinhSV;
    private String emailSV;
    private String maLop;
    private String ngaysinhSV;
    private String quequanSV;

    public SinhVien()
    {}



}
