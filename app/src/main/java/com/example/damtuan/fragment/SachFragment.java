package com.example.damtuan.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.damtuan.DAO.LoaiSachDAO;
import com.example.damtuan.DAO.PhieuMuonDAO;
import com.example.damtuan.DAO.SachDAO;
import com.example.damtuan.DTO.LoaiSach;
import com.example.damtuan.DTO.Sach;
import com.example.damtuan.R;
import com.example.damtuan.adapter.LoaiSachSpinnerAdapter;
import com.example.damtuan.adapter.SachAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;




public class SachFragment extends Fragment {
    RecyclerView rec_Sach;
    EditText edtTimKiem;
    FloatingActionButton fab_Sach;
    SachAdapter sachAdapter;
    SachDAO sachDAO;
    PhieuMuonDAO phieuMuonDAO;
    List<Sach> ds_sach;
    Dialog dialog;
    EditText edTenSach,edGiaSach,edtsoluong;
    Sach sach;
    LoaiSach loaiSach;
    LoaiSachDAO loaiSachDAO;
    List<LoaiSach> ds_LoaiSach;
    LoaiSachSpinnerAdapter loaiSachSpinnerAdapter;
    int position;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rec_Sach=view.findViewById(R.id.rec_Sach);
        fab_Sach=view.findViewById(R.id.fab_ThemSach);
        edtTimKiem=view.findViewById(R.id.edt_TimKiemSach);
        sachDAO=new SachDAO(getActivity());
        phieuMuonDAO=new PhieuMuonDAO(getActivity());
        CapNhapDanhSach();
        fab_Sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(getActivity(),0);
            }
        });

    }
    void CapNhapDanhSach(){
        ds_sach = sachDAO.getAll();
        sachAdapter=new SachAdapter(ds_sach,getActivity(),this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rec_Sach.setLayoutManager(linearLayoutManager);
        rec_Sach.setAdapter(sachAdapter);
        sachAdapter.setonclick(new SachAdapter.onclick() {
            @Override
            public void onItemClick(Sach x) {
                sach=x;
                openDialog(getActivity(),1);
            }
        });
edtTimKiem.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        List<Sach> list=sachDAO.getAll();
        ds_sach.clear();
        for (Sach x:list ) {
            if(x.getSoLuong()<= Integer.parseInt((String) charSequence)|| String.valueOf(x.getSoLuong()).contains(charSequence)){
                ds_sach.add(x);
            }
        }
        sachAdapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
});
    }
    protected void openDialog(final Context context, final int tyte) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.sach_dialog);
        TextView tvMaSach=dialog.findViewById(R.id.tv_ThemMaSach);
        edTenSach=dialog.findViewById(R.id.edThemTenSach);
        edGiaSach=dialog.findViewById(R.id.edThemGiaSach);
        edtsoluong=dialog.findViewById(R.id.edt_soluongsachthem);
        Spinner spinnerLoaiSach=dialog.findViewById(R.id.spiner_LoaiSach);
         Button btnCancel = dialog.findViewById(R.id.btnCacelSach);
        Button  btnSave = dialog.findViewById(R.id.btnSaveSach);
        ds_LoaiSach=new ArrayList<>();
        loaiSachDAO=new LoaiSachDAO(context);
        ds_LoaiSach=loaiSachDAO.getAll();
        loaiSachSpinnerAdapter=new LoaiSachSpinnerAdapter(context,ds_LoaiSach);
        spinnerLoaiSach.setAdapter(loaiSachSpinnerAdapter);
        spinnerLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loaiSach = ds_LoaiSach.get(i);
                Toast.makeText(context, "chọn " + ds_LoaiSach.get(i).getTenLoai(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Xử lý khi không có mục nào được chọn
            }
        });

        //kiểm tra tyte insert 0 hya update 1
        if (tyte != 0) {
            tvMaSach.setText(String.valueOf(sach.getMaSach()));
            edTenSach.setText(sach.getTenSach());
            edGiaSach.setText(String.valueOf(sach.getGiaThue()));
            if (loaiSach != null) {
                for (int i=0;i<ds_LoaiSach.size();i++){
                    if(  loaiSach.getMaLoai()==(ds_LoaiSach.get(i).getMaLoai())){
                        position=i;
                    }
                    spinnerLoaiSach.setSelection(position);
            }

            }
        }
        btnCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()>0){
                    sach=new Sach();
                    sach.setTenSach(edTenSach.getText().toString());
                    sach.setGiaThue(Integer.parseInt(edGiaSach.getText().toString()));
                    sach.setSoLuong(Integer.parseInt(edtsoluong.getText().toString()));
                    if (loaiSach != null) {
                        sach.setMaLoai(loaiSach.getMaLoai());
                    }
                    if(tyte==0){
                        if(sachDAO.insert(sach)>0){
                            Toast.makeText(context, "thêm thành công", Toast.LENGTH_SHORT).show();
                            CapNhapDanhSach();
                        } else {
                            Toast.makeText(context, "thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        sach.setMaSach(Integer.parseInt(tvMaSach.getText().toString()));
                        Log.d("pition",String.valueOf(sach.getMaSach()));
                        if(sachDAO.update(sach)>0){
                            int vitri=-1;
                            for (int i=0; i<ds_sach.size();i++){
                                if(ds_sach.get(i).getMaSach()==sach.getMaSach()){
                                    vitri=i;
                                }
                            }
                            if(vitri != -1){
                                ds_sach.set(vitri,sach);
                                sachAdapter.notifyDataSetChanged();
                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                sachAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }

                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    public int validate() {
        int check = 1;
        String TenSach,GiaSach;
        TenSach=edTenSach.getText().toString();
        GiaSach=edGiaSach.getText().toString();
        if (TenSach.trim().isEmpty()||GiaSach.trim().isEmpty()) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (!isNumeric(GiaSach)) {
            Toast.makeText(getContext(), "Gía sách không hợp lệ", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    public  boolean isNumeric(String str) {
        try {
            // Sử dụng phương thức parseInt để cố gắng chuyển đổi chuỗi thành số nguyên
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            // Nếu có ngoại lệ NumberFormatException xảy ra, chuỗi không phải là số
            return false;
        }
    }
    public void xoa(Sach x) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xoá hay không?");
        builder.setCancelable(true);
        builder.setPositiveButton("yes", (dialog, id) -> {
            sachDAO.delete(String.valueOf(x.getMaSach()));
            phieuMuonDAO.updateMaSachNull(String.valueOf(x.getMaSach()));
          ds_sach.remove(x);
          sachAdapter.notifyDataSetChanged();
            dialog.cancel();
        });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sach, container, false);
    }
}