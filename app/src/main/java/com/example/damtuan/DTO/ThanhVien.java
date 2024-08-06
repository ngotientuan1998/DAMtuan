package com.example.damtuan.DTO;

import java.io.Serializable;

public class ThanhVien implements Serializable {
    int maTv;
    String hoten;
    String namSinh;
    int stk;

    public ThanhVien(int maTv, String hoten, String namSinh, int stk) {
        this.maTv = maTv;
        this.hoten = hoten;
        this.namSinh = namSinh;
        this.stk = stk;
    }

    public int getStk() {
        return stk;
    }

    public void setStk(int stk) {
        this.stk = stk;
    }

    public ThanhVien() {
    }

    public int getMaTv() {
        return maTv;
    }

    public void setMaTv(int maTv) {
        this.maTv = maTv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }
}
