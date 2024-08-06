package com.example.damtuan.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.damtuan.DTO.ThanhVien;
import com.example.damtuan.R;
import com.example.damtuan.fragment.ThanhVienFragment;

import java.util.List;

public class ThanhVienAdapter extends BaseAdapter {
    private Context context;
    private List<ThanhVien> list;
    ThanhVienFragment thanhVienFragment;


    public ThanhVienAdapter(Context context, List<ThanhVien> list,ThanhVienFragment fragment) {
        this.context = context;
        this.list = list;
        this.thanhVienFragment=fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        view = inflater.inflate(R.layout.thanh_vien_item, viewGroup, false);
        TextView tvMaTV, tvTenTV, tvNamSinh,tvstk;
        ImageView imDel;
        ThanhVien x=list.get(i);
        tvMaTV = view.findViewById(R.id.tvMaTV);
        tvTenTV = view.findViewById(R.id.tvTenTV);
        tvNamSinh = view.findViewById(R.id.tvNamSinh);
        tvstk=view.findViewById(R.id.tvSTK);
        imDel = view.findViewById(R.id.imgDeleteLS);
        tvMaTV.setText("Mã thành viên:"+String.valueOf(x.getMaTv()));
        tvNamSinh.setText("Năm sinh:"+x.getNamSinh().toString());
        tvTenTV.setText("Họ tên:"+x.getHoten().toString());
        tvstk.setText("stk:"+x.getStk());
        if(x.getStk()%5==0){
            tvstk.setTextColor(Color.RED);
        } else {
            tvstk.setTextColor(Color.GREEN);
        }

        imDel.setOnClickListener(view1 -> {
            thanhVienFragment.xoa(String.valueOf(x.getMaTv()));
        });
        return view;
    }


}
