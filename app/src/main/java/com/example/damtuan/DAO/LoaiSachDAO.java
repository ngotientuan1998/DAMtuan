package com.example.damtuan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.damtuan.DTO.LoaiSach;
import com.example.damtuan.database.DbHelper;

import java.util.ArrayList;
import java.util.List;



public class LoaiSachDAO {
    private SQLiteDatabase db;

    public LoaiSachDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(LoaiSach x) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", x.getTenLoai());
        return db.insert("LoaiSach", null, values);
    }

    public int update(LoaiSach x) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", x.getTenLoai());
        return db.update("LoaiSach", values, "maLoai=?", new String[]{String.valueOf(x.getMaLoai())});
    }

    public int delete(String id) {
        String sql="SELECT * FROM Sach ";
        return db.delete("LoaiSach", "maLoai=?", new String[]{id});
    }

    public List<LoaiSach> getAll(){
        String sql="SELECT * FROM LoaiSach";
        return getData(sql);
    }
    private List<LoaiSach> getData(String sql, String...selectionArgs) {
        List<LoaiSach> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            LoaiSach x = new LoaiSach();
            x.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maLoai"))));
            x.setTenLoai(c.getString(c.getColumnIndex("tenLoai")));
            list.add(x);
        }
        c.close();
        return list;
    }
    public LoaiSach getID(String id) {
        String sql = "SELECT * FROM LoaiSach WHERE maLoai=?";
        List<LoaiSach> lis = getData(sql, id);
        if (!lis.isEmpty()) {
            return lis.get(0);
        } else {
            return null; // Trả về null nếu không tìm thấy dữ liệu phù hợp
        }
    }



}
