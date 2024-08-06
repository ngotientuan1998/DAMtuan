package com.example.damtuan.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.damtuan.DAO.PhieuMuonDAO;
import com.example.damtuan.DAO.SachDAO;
import com.example.damtuan.DAO.thanhVienDAO;
import com.example.damtuan.DTO.PhieuMuon;
import com.example.damtuan.DTO.Sach;
import com.example.damtuan.DTO.ThanhVien;
import com.example.damtuan.R;
import com.example.damtuan.adapter.PhieuMuonAdapter;
import com.example.damtuan.adapter.ThanhVienSpinnerAdapter;
import com.example.damtuan.adapter.sachSpinnerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




public class PhieuMuonFragment extends Fragment {
    List<PhieuMuon> ds_PhieuMuon;
    List<Sach> ds_Sach;
    List<ThanhVien> ds_ThanhVienn;
    PhieuMuonDAO phieuMuonDAO;
    SachDAO sachDAO;
    thanhVienDAO thanhVienDAO;
    Sach sach;
    ThanhVien thanhVien;
    RecyclerView rec_Phieu;
    FloatingActionButton fab_Phieu;
    PhieuMuonAdapter phieuMuonAdapter;

    sachSpinnerAdapter sachSpinnerAdapters;
    ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    TextView tvMaPhieu;
    TextView tvngay;
    TextView tvTien;

    CheckBox chk_TranhThai;
    PhieuMuon phieuMuon;
    int viTriSach, viTriThanhVien;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar ;
    int gio,phut;

//addTextChangedListener(new TextWatcher()

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rec_Phieu = view.findViewById(R.id.rec_PhieuMuon);
        fab_Phieu = view.findViewById(R.id.fab_ThemPhieuMuon);
        phieuMuonDAO = new PhieuMuonDAO(getActivity());
        CapNhap();
        calendar = Calendar.getInstance();

        fab_Phieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(getActivity(), 0);
            }
        });
    }

    void CapNhap() {
        ds_PhieuMuon = phieuMuonDAO.getAll();
        phieuMuonAdapter = new PhieuMuonAdapter(ds_PhieuMuon, getActivity(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rec_Phieu.setLayoutManager(linearLayoutManager);
        rec_Phieu.setAdapter(phieuMuonAdapter);
        phieuMuonAdapter.setonclick(new PhieuMuonAdapter.onclick() {
            @Override
            public void onItemClick(PhieuMuon x) {
                phieuMuon = x;
                openDialog(getActivity(), 1);
            }
        });


    }


    protected void openDialog(final Context context, final int tyte) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.phieu_muon_dialog, null);
        tvMaPhieu = view.findViewById(R.id.tv_PhieuThem);
        tvngay = view.findViewById(R.id.tv_NgayThem);
        tvTien = view.findViewById(R.id.tv_TienThueThemPM);
        Spinner spinnerSach = view.findViewById(R.id.spine_SachPM);
        Spinner spinnerThanhVien = view.findViewById(R.id.spiner_ThanhVienPM);
        chk_TranhThai = view.findViewById(R.id.chk_TraSach);
        Button btnCancel = view.findViewById(R.id.btnCacelPM);
        Button btnSave = view.findViewById(R.id.btnSavePM);
        ds_Sach = new ArrayList<>();
        ds_ThanhVienn = new ArrayList<>();
        sachDAO = new SachDAO(context);
        thanhVienDAO = new thanhVienDAO(context);
        ds_Sach = sachDAO.getAll();
        ds_ThanhVienn = thanhVienDAO.getAll();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        AlertDialog dialog = builder.create();
        if (ds_Sach != null && ds_ThanhVienn != null) {
            sachSpinnerAdapters = new sachSpinnerAdapter(context, ds_Sach);
            spinnerSach.setAdapter(sachSpinnerAdapters);
            spinnerSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    sach = ds_Sach.get(i);
                    Toast.makeText(context, "chọn " + ds_Sach.get(i).getTenSach(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // Xử lý khi không có mục nào được chọn
                }
            });
            thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context, ds_ThanhVienn);
            spinnerThanhVien.setAdapter(thanhVienSpinnerAdapter);
            spinnerThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    thanhVien = ds_ThanhVienn.get(i);
                    Toast.makeText(context, "chọn " + ds_ThanhVienn.get(i).getHoten(), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            Date date = Calendar.getInstance().getTime();
            String ngay = sdf.format(date);
            gio=calendar.get(Calendar.HOUR_OF_DAY);
            phut=calendar.get(Calendar.MINUTE);
            tvngay.setText(ngay);
            //kiểm tra tyte insert 0 hya update 1
            if (tyte != 0) {
                tvMaPhieu.setText(String.valueOf(phieuMuon.getMaPM()));
                tvTien.setText(String.valueOf(phieuMuon.getTienThue()));
                if (phieuMuon.getTraSach() == 1) {
                    chk_TranhThai.setChecked(true);
                } else {
                    chk_TranhThai.setChecked(false);
                }
                if (sach != null) {
                    for (int i = 0; i < ds_Sach.size(); i++) {
                        if (sach.getMaSach() == (ds_Sach.get(i).getMaSach())) {
                            viTriSach = i;
                        }
                        spinnerSach.setSelection(viTriSach);
                    }

                }

                if (thanhVien != null) {
                    for (int i = 0; i < ds_ThanhVienn.size(); i++) {
                        if (thanhVien.getMaTv() == (ds_ThanhVienn.get(i).getMaTv())) {
                            viTriThanhVien = i;
                        }
                        spinnerThanhVien.setSelection(viTriThanhVien);
                    }

                }
            }
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhieuMuon phieuMuon1 = new PhieuMuon();
                    SharedPreferences preferences = getContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                    String matt = preferences.getString("USERNAME", "");
                    phieuMuon1.setMaTT(matt);
                    phieuMuon1.setMaSach(sach.getMaSach());
                    phieuMuon1.setMaTv(thanhVien.getMaTv());
                    phieuMuon1.setNgay(ngay);
                    phieuMuon1.setTienThue(sach.getGiaThue());
                    int TrangThai = 0;
                    if (chk_TranhThai.isChecked()) {
                        TrangThai = 1;
                    }
                    phieuMuon1.setTraSach(TrangThai);

                    if (validate() > 0) {
                        if (tyte == 0) {

                            if (phieuMuonDAO.insert(phieuMuon1) > 0) {
                                CapNhap();
                                Toast.makeText(context, "thêm thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, "thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            phieuMuon1.setMaPM(Integer.parseInt(tvMaPhieu.getText().toString()));
                            if (phieuMuonDAO.update(phieuMuon1) > 0) {
                                int vitri = -1;
                                for (int i = 0; i < ds_PhieuMuon.size(); i++) {
                                    if (ds_PhieuMuon.get(i).getMaPM() == phieuMuon1.getMaPM()) {
                                        vitri = i;
                                    }
                                }
                                if (vitri != -1) {
                                    ds_PhieuMuon.set(vitri, phieuMuon1);
                                    phieuMuonAdapter.notifyDataSetChanged();
                                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                            }

                        }


                    }
                }
            });
        } else {

        }
        dialog.show();
    }

    public int validate() {
        int check = 1;
        if (tvTien.getText().length() == 0 || tvngay.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    public void xoa(PhieuMuon x) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xoá hay không?");
        builder.setCancelable(true);
        builder.setPositiveButton("yes", (dialog, id) -> {
            phieuMuonDAO.delete(String.valueOf(x.getMaPM()));
            ds_PhieuMuon.remove(x);
            phieuMuonAdapter.notifyDataSetChanged();
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
        return inflater.inflate(R.layout.fragment_phieu_muon, container, false);
    }
}