package com.example.doanqlsv.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doanqlsv.MainActivity_GiangVien;
import com.example.doanqlsv.Model.GiangVien;
import com.example.doanqlsv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

//Sửa lại nha cứ giữ (t chỉ copy qua à)
//cứ giữ đoạn hình theo giới tính
public class InforGiangVien_Fragment extends Fragment {

    private  View view;
    private MainActivity_GiangVien mainActivity_giangvien;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public final CollectionReference referenceSV = firestore.collection("Khoa");
    ImageView anh;
    TextView mEmailGv, mChucDanh, mHocVi, mKhoa, mHoTenGv;
    GiangVien gv;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull  ViewGroup container,@NonNull
    Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_infor_giang_vien_, container, false);
        mainActivity_giangvien = (MainActivity_GiangVien) getActivity();
        gv=mainActivity_giangvien.getGv();
        init();
        mEmailGv.setText(gv.getEmailGV());
        mHocVi.setText(gv.getHocVi());
        mChucDanh.setText(gv.getChucDanh());
        mHoTenGv.setText(gv.getHoGV()+" "+gv.getTenGV());
        String n = gv.getGioiTinhGV();
        if (gv != null) {
            if ("Nam".equalsIgnoreCase(gv.getGioiTinhGV())) {
                // Set the male avatar image
                anh.setImageResource(R.drawable.man_ava);
            } else if ("Nữ".equalsIgnoreCase(gv.getGioiTinhGV())) {
                // Set the female avatar image
                anh.setImageResource(R.drawable.woman_ava);
            }
        }
        referenceSV.whereEqualTo("maKhoa",gv.getMaKhoa()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()&&!task.getResult().isEmpty())
                {
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentID = documentSnapshot.getString("tenKhoa");
                    mKhoa.setText(documentID);
                }
            }
        });
        return view;
    }
    private void init()
    {
        mEmailGv = view.findViewById(R.id.emailgv);
        mChucDanh = view.findViewById(R.id.cdgv);
        mHoTenGv = view.findViewById(R.id.htgv);
        mHocVi = view.findViewById(R.id.hvgv);
        mKhoa = view.findViewById(R.id.khoa);
        anh = view.findViewById(R.id.Sphoto1);
    }
}
