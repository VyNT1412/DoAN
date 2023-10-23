package com.example.doanqlsv.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.doanqlsv.Model.Diem;
import com.example.doanqlsv.Model.MonDK;
import com.example.doanqlsv.R;

import java.util.ArrayList;

public class LichHocAdapter extends ArrayAdapter<MonDK> {
    public LichHocAdapter(ArrayList<MonDK> d, Context context, int res)
    {
        super(context,res,d);
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        MonDK d = getItem(position);
        if(view==null)
        {
            view= LayoutInflater.from(getContext()).inflate(R.layout.item_lich_hoc,parent,false);
        }
        TextView mamon = (TextView) view.findViewById(R.id.mamh);
        TextView  tengv= (TextView) view.findViewById(R.id.tengvday);
        TextView ngayhc_giohoc = (TextView) view.findViewById(R.id.ngayday_gioBD_KT);
        mamon.setText("Mã môn: "+d.getMaMon());
        tengv.setText("Giảng viên dạy: "+d.getHoGV()+" "+d.getTenGV());
        ngayhc_giohoc.setText("Ngày học: "+d.getNgayDay()+": "+d.getGioDay()+" - "+d.getGioKT());
       return view;
    }
}
