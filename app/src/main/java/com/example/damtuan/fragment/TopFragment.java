package com.example.damtuan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.damtuan.DAO.ThongKeDAO;
import com.example.damtuan.DTO.Top;
import com.example.damtuan.R;
import com.example.damtuan.adapter.topAdapter;

import java.util.List;



public class TopFragment extends Fragment {
RecyclerView rec_Top;
topAdapter Adapter;
ThongKeDAO thongKeDAO;
List<Top> ds_Top;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rec_Top=view.findViewById(R.id.rec_Top);
        thongKeDAO=new ThongKeDAO(getActivity());
        CapNhap();
    }

    void  CapNhap(){
        ds_Top=thongKeDAO.getTop();
        Adapter=new topAdapter(getActivity(),ds_Top);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rec_Top.setLayoutManager(linearLayoutManager);
        rec_Top.setAdapter(Adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false);
    }
}