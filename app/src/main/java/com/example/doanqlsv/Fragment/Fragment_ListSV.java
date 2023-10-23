package com.example.doanqlsv.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanqlsv.Adapter.GiangVienAdapter;
import com.example.doanqlsv.Adapter.SinhVienAdapter;
import com.example.doanqlsv.Model.GiangVien;
import com.example.doanqlsv.Model.SinhVien;
import com.example.doanqlsv.R;
import com.example.doanqlsv.SignUpStudentActivity;
import com.example.doanqlsv.SignUpTeacherActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Fragment_ListSV extends Fragment {
    private View view;
    RecyclerView recyclerViewSv;
    ArrayList<SinhVien> listSv;
    SinhVienAdapter sinhVienAdapter;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public final CollectionReference referenceSV = firestore.collection("SinhVien");
    Spinner spinnerKhoa;
    Spinner spinnerLop;
    Button btnsearchSv, btnsearchLop;
    FloatingActionButton bntThemSv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment__list_sv, container, false);
        recyclerViewSv = view.findViewById(R.id.table_sv);
        spinnerKhoa= view.findViewById(R.id.spinnerKHOAlistSV);
        spinnerLop= (Spinner) view.findViewById(R.id.spinnerLOPListSV);
        btnsearchLop=(Button) view.findViewById(R.id.btnSearchLop);
        btnsearchSv=view.findViewById(R.id.SearchKhoaSV);
        bntThemSv=view.findViewById(R.id.floatingActionButton1);
        firestore.collection("Khoa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshot = task.getResult();
                    if (snapshot != null) {
                        List<String> list = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : snapshot) {
                            String maKhoa = doc.getString("maKhoa");
                            list.add(maKhoa);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerKhoa.setAdapter(adapter);
                    }
                }
            }
        });

        btnsearchSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    listSv = new ArrayList<SinhVien>();
                    sinhVienAdapter = new SinhVienAdapter(listSv);
                    recyclerViewSv.setAdapter(sinhVienAdapter);

                String makhoa = spinnerKhoa.getSelectedItem().toString();
                referenceSV.whereEqualTo("maKhoa",makhoa).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    SinhVien s;
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<String> list= new ArrayList<String>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                s = new SinhVien(document.getString("maSV"),document.getString("hoSV"), document.getString("maKhoa"),document.getString("tenSV"), document.getString("passwordSV"), document.getString("gioitinhSV"), document.getString("emailSV"), document.getString("maLop"), document.getString("ngaysinhSV"), document.getString("quequanSV"));
                                listSv.add(s);
                        }
                            sinhVienAdapter = new SinhVienAdapter(listSv);
                            recyclerViewSv.setAdapter(sinhVienAdapter);
                        firestore.collection("Lop").whereEqualTo("maKhoa",makhoa).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            SinhVien s;
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<String> list= new ArrayList<String>();
                                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                    String malop = document.getString("maLop");
                                    list.add(malop);
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, list);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerLop.setAdapter(adapter);
                            }
                        });
                    }
                });
            }

        });
        btnsearchLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String malop=spinnerLop.getSelectedItem().toString();
                firestore.collection("SinhVien").whereEqualTo("maLop",malop).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    SinhVien s;
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                ArrayList<SinhVien> list= new ArrayList<SinhVien>();
                                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                    s = new SinhVien(document.getString("maSV"),document.getString("hoSV"), document.getString("maKhoa"),document.getString("tenSV"), document.getString("passwordSV"), document.getString("gioitinhSV"), document.getString("emailSV"), document.getString("maLop"), document.getString("ngaysinhSV"), document.getString("quequanSV"));
                                    list.add(s);
                                }
                                sinhVienAdapter = new SinhVienAdapter(list);
                                recyclerViewSv.setAdapter(sinhVienAdapter);
                            }
                        })      ;
            }
        });
        bntThemSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), SignUpStudentActivity.class);
                startActivity(i);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewSv.setLayoutManager(linearLayoutManager);
        return view;
    }

}