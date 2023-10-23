package com.example.doanqlsv.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.doanqlsv.Model.Diem;
import com.example.doanqlsv.R;

import java.util.ArrayList;

public class DiemAdapter extends ArrayAdapter<Diem> {
    Context context;
    LayoutInflater inflater;
    ArrayList<Diem> list;
    public DiemAdapter(ArrayList<Diem> d,Context context,int res)
    {
        super(context,res,d);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
      Diem d = getItem(position);
      if(view==null)
      {
          view=LayoutInflater.from(getContext()).inflate(R.layout.table_score,parent,false);
      }
      TextView mssv = (TextView) view.findViewById(R.id.maSSV);
      TextView mhk=(TextView) view.findViewById(R.id.mahocki);
      TextView diemgk = (TextView) view.findViewById(R.id.DiemMon1);
      TextView diemck = (TextView) view.findViewById(R.id.DiemMon2);
      TextView mamon = (TextView) view.findViewById(R.id.mamonhoc) ;
      mssv.setText(d.getMaSV());
      mhk.setText(String.valueOf(d.getMaHK()));
      mamon.setText(d.getMaMon());
      diemck.setText(String.valueOf(d.getDiemCK()));
      diemgk.setText(String.valueOf(d.getDiemGK()));
      return view;
    }
}
