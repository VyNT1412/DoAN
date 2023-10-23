package com.example.doanqlsv.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.doanqlsv.Adapter.LichHocAdapter;
import com.example.doanqlsv.Adapter.XemDiemAdapter;
import com.example.doanqlsv.MainActivity_SinhVien;
import com.example.doanqlsv.Model.Diem;
import com.example.doanqlsv.Model.MonDK;
import com.example.doanqlsv.Model.SinhVien;
import com.example.doanqlsv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LichHoc_Fragment extends Fragment {
    View view;
    ListView lichhoc;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public final CollectionReference referenceSV = firestore.collection("SinhVien");
    private MainActivity_SinhVien mainActivity_sinhVien;
    SinhVien s;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull  ViewGroup container,
                             @NonNull  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_lich_hoc_sv, container, false);
        mainActivity_sinhVien = (MainActivity_SinhVien) getActivity();
        s = mainActivity_sinhVien.getS();
        lichhoc = (ListView) view.findViewById(R.id.lichhoc);
        loadLich(view);
        return view;
    }
    private void loadLich(View view)
    {
        firestore.collection("MonDK").whereArrayContains("maSV",s.getMaSV()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            MonDK d;
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    QuerySnapshot snapshot = task.getResult();
                    if (snapshot != null) {
                        List<MonDK> list = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : snapshot) {
                            String email = doc.getString("emailGV");
                            String gioday = doc.getString("gioDay");
                            String giokt = doc.getString("gioKT");
                            String hogv = doc.getString("hoGV");
                            String tengv = doc.getString("tenGV");
                            int mahk = doc.get("maHK",Integer.class);
                            String maKhoa = doc.getString("maKhoa");
                            String mamon = doc.getString("maMon");
                            List<String> dssv = (List<String>) doc.get("maSV");
                            String ngayday = doc.getString("ngayDay");
                            d = new MonDK(email,hogv,tengv,gioday,giokt,mahk,maKhoa,mamon,dssv,ngayday);
                            list.add(d);
                        }
                        LichHocAdapter diemAdapter = new LichHocAdapter((ArrayList<MonDK>) list, view.getContext(), android.R.layout.simple_list_item_1);
                        lichhoc.setAdapter(diemAdapter);
                    }
                }
            }
        });
    }
}