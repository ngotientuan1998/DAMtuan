package com.example.damtuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.damtuan.DTO.Top;
import com.example.damtuan.R;

import java.util.List;

public class topAdapter extends RecyclerView.Adapter<topAdapter.viewHolder> {
    Context context;
    List<Top> list;

    public topAdapter(Context context, List<Top> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top, parent, false);
        return new topAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Top top=list.get(position);
        holder.tvTenSach.setText("tên:"+top.getTenSach());
        holder.Tvtop.setText("số lượng:"+String.valueOf(top.getSoLuong()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView tvTenSach,Tvtop;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach=itemView.findViewById(R.id.tv_TenSachTop);
            Tvtop=itemView.findViewById(R.id.tv_soLuongTop);
        }
    }
}
