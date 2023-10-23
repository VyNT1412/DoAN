package com.example.doanqlsv.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanqlsv.Adapter.DiemAdapter;
import com.example.doanqlsv.Adapter.XemDiemAdapter;
import com.example.doanqlsv.MainActivity_SinhVien;
import com.example.doanqlsv.Model.Diem;
import com.example.doanqlsv.Model.SinhVien;
import com.example.doanqlsv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class XemDiem_SV_Fragment extends Fragment {
    View view;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public final CollectionReference referenceSV = firestore.collection("SinhVien");
    private MainActivity_SinhVien mainActivity_sinhVien;
    SinhVien s;
    Spinner hk;
    Button c;
    ListView listds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_xem_diem_sv, container, false);
        mainActivity_sinhVien = (MainActivity_SinhVien) getActivity();
        hk = (Spinner) view.findViewById(R.id.spinnerKi);
        s = mainActivity_sinhVien.getS();
        c = (Button) view.findViewById(R.id.chonhk);
        listds = (ListView) view.findViewById(R.id.dsdiem);
        loadHocKi(view);
        loadDiem(s);
        // Inflate the layout for this fragment
        return view;
    }

    private void loadHocKi(View view) {
        firestore.collection("HocKy").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshot = task.getResult();
                    if (snapshot != null) {
                        List<Integer> list = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : snapshot) {
                            int maKhoa = doc.get("maHK", Integer.class);
                            list.add(maKhoa);
                        }
                        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        hk.setAdapter(adapter);
                    }
                }
            }
        });
    }

    private void loadDiem(SinhVien s) {
        List<Diem> list = new ArrayList<>();
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                XemDiemAdapter diemAdapter = new XemDiemAdapter((ArrayList<Diem>) list, view.getContext(), android.R.layout.simple_list_item_1);
                listds.setAdapter(diemAdapter);
                int id = Integer.parseInt(hk.getSelectedItem().toString());
                String msv = s.getMaSV();
                firestore.collection("Diem").whereEqualTo("maHK", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    Diem d;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<Diem> listD = new ArrayList<>();

                            QuerySnapshot snapshot = task.getResult();
                            for (QueryDocumentSnapshot doc : snapshot) {
                                String mm = doc.getString("maMon");
                                double diemGk = doc.getDouble("diemGK");
                                double diemCk = doc.getDouble("diemCK");
                                String mssv = doc.getString("maSV");
                                String emailGv = doc.getString("emailGV");
                                d = new Diem(diemCk, diemGk, emailGv, id, mssv, mm);
                                if(s.getMaSV().equals(mssv)) {
                                    listD.add(d);
                                }
                            }
                            XemDiemAdapter diemAdapter = new XemDiemAdapter((ArrayList<Diem>) listD, view.getContext(), android.R.layout.simple_list_item_1);
                            listds.setAdapter(diemAdapter);
                        }


                        if (task.getResult().isEmpty()) {
                            Toast.makeText(mainActivity_sinhVien, "Kỳ học đó bạn không có môn học hoặc môn học chưa đươc lên điểm", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }

        });
    }
}