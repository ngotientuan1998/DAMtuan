package com.example.damtuan.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.damtuan.DAO.SachDAO;
import com.example.damtuan.DTO.Sach;
import com.example.damtuan.R;
import com.example.damtuan.fragment.SachFragment;

import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.viewHolder>{
    List<Sach> ds_sach;
    Context context;
    SachFragment sachFragment;
    SachDAO sachDAO;
    public interface onclick {
        void onItemClick(Sach x);
    }

    onclick onclicks;
    public void  setonclick(onclick x){
        this.onclicks=x;
    }

    public SachAdapter(List<Sach> ds_sach, Context context,SachFragment x) {
        this.ds_sach = ds_sach;
        this.context = context;
        this.sachFragment=x;
        sachDAO=new SachDAO(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sach_item, parent, false);
        return new SachAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Sach x=ds_sach.get(position);
        holder.tvMaSach.setText("mã:"+String.valueOf(x.getMaSach()));
        holder.tvTenSach.setText("tên:"+x.getTenSach());
        holder.tvGiaSach.setText(String.valueOf("giá:"+x.getGiaThue()));
        holder.tvsl.setText(String.valueOf("soLuong:"+x.getSoLuong()));
        String maLoaiText = (x.getMaLoai() == -1) ? "Trống" : String.valueOf(x.getMaLoai());
        holder.tvMaLoai.setText("Mã loại: " + maLoaiText);
        if(x.getMaLoai()==-1){
            holder.tvMaLoai.setText("mã loại:Trống");
        } else {
            holder.tvMaLoai.setText(String.valueOf("mã loại:"+x.getMaLoai()));
        }
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sachFragment.xoa(x);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicks.onItemClick(x);
            }
        });
        holder.tvsl.setText(String.valueOf("số lượng"+x.getSoLuong()));
        holder.tvsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialong(x);
            }
        });

    }
    public  void  dialong(Sach x){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialong_soluong, null);
        TextView tvTenSanPham;
        EditText edtSoLuong;
        tvTenSanPham=view.findViewById(R.id.tv_TenSachsl);
        edtSoLuong=view.findViewById(R.id.edt_soLuongSach);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        tvTenSanPham.setText(x.getTenSach());
        String soLuong=edtSoLuong.getText().toString();
        if(soLuong.trim().isEmpty()){
            Toast.makeText(context, "số lượng trống", Toast.LENGTH_SHORT).show();
        } else {
            x.setSoLuong(Integer.parseInt(soLuong));
            sachDAO.update(x);
        }
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return ds_sach.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView tvMaSach,tvMaLoai, tvTenSach,tvGiaSach,tvsl;
        private ImageView btnRemove;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaSach=itemView.findViewById(R.id.tv_MaSach);
            tvMaLoai = itemView.findViewById(R.id.tv_SachThuocLoai);
            tvTenSach = itemView.findViewById(R.id.tv_TenSach);
            tvGiaSach=itemView.findViewById(R.id.tv_GiaSach);
            tvsl=itemView.findViewById(R.id.tv_SachSL);
            btnRemove = itemView.findViewById(R.id.imXoaSach);
        }
    }
}
