package com.example.doanqlsv.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doanqlsv.R;


public class DiemMonHoc_Fragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull  ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diem_mon_hoc_, container, false);
    }
}