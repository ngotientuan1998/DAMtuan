package com.example.damtuan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.damtuan.DTO.ThanhVien;
import com.example.damtuan.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class thanhVienDAO {
    private SQLiteDatabase db;

    public thanhVienDAO(Context context){
        DbHelper dbHelper= new DbHelper(context);
        db=dbHelper.getWritableDatabase();
    }
    public long insert(ThanhVien x){
        ContentValues values = new ContentValues();
        values.put("hoTen", x.getHoten());
        values.put("namSinh", x.getNamSinh());
        values.put("stk", x.getStk());
        return db.insert("ThanhVien", null, values);
    }
    public int update(ThanhVien x) {
        ContentValues values = new ContentValues();
        values.put("hoTen", x.getHoten());
        values.put("namSinh", x.getNamSinh());
        return db.update("ThanhVien", values, "maTV=?", new String[]{String.valueOf(x.getMaTv())});
    }

    public int delete(String id) {
        return db.delete("ThanhVien", "maTV=?", new String[]{id});
    }

    public List<ThanhVien> getAll(){
        String sql="SELECT * FROM ThanhVien";
        return getData(sql);
    }
    private    List<ThanhVien> getData(String sql,String...selectionArgs){
        List<ThanhVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            ThanhVien obj = new ThanhVien();
            obj.setMaTv(Integer.parseInt(c.getString(c.getColumnIndex("maTV"))));
            obj.setHoten(c.getString(c.getColumnIndex("hoTen")));
            obj.setNamSinh(c.getString(c.getColumnIndex("namSinh")));
            obj.setStk(Integer.parseInt(c.getString(c.getColumnIndex("namSinh"))));
            list.add(obj);

        }
        c.close();
        return list;
    }
    public  ThanhVien getID(String id){
        String sql="SELECT * FROM ThanhVien WHERE maTV=?";
        List<ThanhVien> lis=getData(sql,id);
        if (!lis.isEmpty()&&lis != null) {
            return lis.get(0);
        } else {
            return null;
        }
    }
}
