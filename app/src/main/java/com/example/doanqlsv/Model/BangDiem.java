package com.example.doanqlsv.Model;


public class BangDiem {
    private String STT;
    private String maMon;
    private String tenLop;
    private String tenMon;
    private String tinChi;
    private String quaTrinh;
    private String diemThi;
    private String diemTK4;
    private String diemTK10;
    private String ketQua;

    public BangDiem(String STT, String maMon, String tenLop, String tenMon, String tinChi,
                    String quaTrinh, String diemThi, String diemTK4, String diemTK10, String ketQua) {
        this.setSTT(STT);
        this.setMaMon(maMon);
        this.setTenLop(tenLop);
        this.setTenMon(tenMon);
        this.setTinChi(tinChi);
        this.setQuaTrinh(quaTrinh);
        this.setDiemThi(diemThi);
        this.setDiemTK4(diemTK4);
        this.setDiemTK10(diemTK10);
        this.setKetQua(ketQua);
    }

    // Các phương thức getter cho các thuộc tính của MonHoc
    public String getSTT() {return STT;}

    public String getMaMon() {return maMon;}

    public String getTenLop() {return tenLop;}

    public String getTenMon() {return tenMon;}

    public String getTinChi() {return tinChi;}

    public String getQuaTrinh() {return quaTrinh;}

    public String getDiemThi() {return diemThi;}

    public  String getDiemTK4(){return diemTK4;}

    public  String getDiemTK10(){return diemTK10;}

    public  String getKetQua(){return ketQua;}

    public void setSTT(String STT) {
        this.STT = STT;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public void setTinChi(String tinChi) {
        this.tinChi = tinChi;
    }

    public void setQuaTrinh(String quaTrinh) {
        this.quaTrinh = quaTrinh;
    }

    public void setDiemThi(String diemThi) {
        this.diemThi = diemThi;
    }

    public void setDiemTK4(String diemTK4) {
        this.diemTK4 = diemTK4;
    }

    public void setDiemTK10(String diemTK10) {
        this.diemTK10 = diemTK10;
    }

    public void setKetQua(String ketQua) {
        this.ketQua = ketQua;
    }
}
