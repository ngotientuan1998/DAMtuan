package com.example.damtuan.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    static  final  String dbName = "PNLIB";
    static  final  int dbVersion = 1;

    public DbHelper( Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tạo bảng thu thu
        String createTableThuThu=
                "create table ThuThu (" +
                        "maTT TEXT PRIMARY KEY, " +
                        "hoTen TEXT NOT NULL, " +
                        "matKhau TEXT NOT NULL)";
        db.execSQL(createTableThuThu);
//        String addThuThu =
//                "INSERT INTO ThuThu (maTT, hoTen, matKhau) " +
//                        "VALUES ('1', 'admin', 'admin');";
//        db.execSQL(addThuThu);
        //Tạo bảng Thanh vien
        String createTableThanhVien=
                "create table ThanhVien (" +
                        "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "hoTen TEXT NOT NULL, " +
                        "stk INTEGER NOT NULL, " +
                        "namSinh TEXT NOT NULL)";
        db.execSQL(createTableThanhVien);

        //Tạo bảng Loại sách
        String createTableLoaiSach=
                "create table LoaiSach (" +
                        "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenLoai TEXT NOT NULL)";
        db.execSQL(createTableLoaiSach);
        //Tạo bảng  sách
        String createTableSach=
                "create table Sach (" +
                        "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenSach TEXT NOT NULL, " +
                        "giaThue INTEGER NOT NULL, " +
                        "soLuong INTEGER NOT NULL, " +
                        "maLoai INTEGER REFERENCES LoaiSach(maLoai))";
        db.execSQL(createTableSach);

        String createTablePhieuMuon=
                "create table PhieuMuon (" +
                        "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "maTT TEXT REFERENCES ThuThu(maTT), " +
                        "maTV INTEGER REFERENCES ThanhVien(maTv), " +
                        "maSach INTEGER REFERENCES Sach(maSach), " +
                        "tienThue INTEGER NOT NULL ," +
                        "ngay DATE NOT NULL ," +
                        "traSach INTEGER NOT NULL )";
        db.execSQL(createTablePhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropTableLoaiThuThu = "drop table if exists ThuThu";
        db.execSQL(dropTableLoaiThuThu);

        String dropTableThanhVien = "drop table if exists ThanhVien";
        db.execSQL(dropTableThanhVien);

        String dropTableLoaiSach = "drop table if exists LoaiSach";
        db.execSQL(dropTableLoaiSach);

        String dropTableSachSach = "drop table if exists SachSach";
        db.execSQL(dropTableSachSach);

        String dropTableLoaiPhieuMuon = "drop table if exists PhieuMuon";
        db.execSQL(dropTableLoaiPhieuMuon);

        onCreate(db);
    }
}
