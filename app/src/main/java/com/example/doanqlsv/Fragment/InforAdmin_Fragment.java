package com.example.doanqlsv.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanqlsv.Admin_MainActivity;
import com.example.doanqlsv.Model.GiangVien;
import com.example.doanqlsv.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class InforAdmin_Fragment extends Fragment {
    private  View view;
    ImageView anh;
    private Admin_MainActivity Admin_mainActivity;
    TextView mEmailAdmin;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public final CollectionReference referenceSV = firestore.collection("GiaoVien");
    GiangVien gv;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull  ViewGroup container,@NonNull
    Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_infor_admin_, container, false);
        Admin_mainActivity = (Admin_MainActivity) getActivity();
        gv = Admin_mainActivity.getGv();
        init();
        mEmailAdmin.setText(gv.getEmailGV());

            if ("Nam".equalsIgnoreCase(gv.getGioiTinhGV())) {
                // Set the male avatar image
                anh.setImageResource(R.drawable.man_ava);
            } else if ("Nữ".equalsIgnoreCase(gv.getGioiTinhGV())) {
                // Set the female avatar image
                anh.setImageResource(R.drawable.woman_ava);
            }

        return view;
    }
    private void init()
    {
        mEmailAdmin = view.findViewById(R.id.emailad);
        anh = view.findViewById(R.id.Sphoto1);
    }
}