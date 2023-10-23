package com.example.doanqlsv.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.doanqlsv.Model.Diem;
import com.example.doanqlsv.R;

import java.util.ArrayList;

public class XemDiemAdapter extends ArrayAdapter<Diem> {


    public XemDiemAdapter(ArrayList<Diem> d,Context context,int res)
    {
        super(context,res,d);
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Diem d = getItem(position);
        if(view==null)
        {
            view= LayoutInflater.from(getContext()).inflate(R.layout.item_xem_diem,parent,false);
        }
        TextView  mamon = (TextView) view.findViewById(R.id.ttmamon);
        TextView   diemgk = (TextView) view.findViewById(R.id.ttdiemgk);
        TextView  diemck = (TextView) view.findViewById(R.id.ttdiemck);
        TextView  he10=(TextView) view.findViewById(R.id.he10);
        TextView  he4=(TextView) view.findViewById(R.id.he4);
        TextView  kq=(TextView) view.findViewById(R.id.ketqua);
        //
        mamon.setText(d.getMaMon());
        diemck.setText(String.valueOf(d.getDiemCK()));
        diemgk.setText(String.valueOf(d.getDiemGK()));
        double h10 = d.getDiemGK()*0.4+d.getDiemCK()*0.6;
        he10.setText(String.format("%.2f",h10));
        double h4= (h10*4)/10;
        he4.setText(String.format("%.2f",h4));
        if(h10 < 4.0)
            kq.setText("Rớt");
        else
            kq.setText("Đậu");
        return view;
    }
}
