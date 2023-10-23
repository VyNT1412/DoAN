package com.example.doanqlsv.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
import com.example.doanqlsv.Model.GiangVien;
import com.example.doanqlsv.R;
import com.example.doanqlsv.SignUpTeacherActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Fragment_ListGV extends Fragment  {

    private View view;
    TextView txt;
    RecyclerView recyclerViewGv;
    ArrayList<GiangVien> listgv;
    GiangVienAdapter giangVienAdapter;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Spinner spinnerKhoa;
    Button btnsearch, btnAll;
    FloatingActionButton bntThem;
    private  final Gson gson=new Gson();
    private SharedPreferences sharedPreferencesGV ;
    private SharedPreferences.Editor editorShare;
    public final CollectionReference referenceGV = firestore.collection("GiaoVien");


    List<String> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment__list_g_v, container, false);
        recyclerViewGv=(RecyclerView) view.findViewById(R.id.table_gv);
        spinnerKhoa = (Spinner) view.findViewById(R.id.spinnerKHOAlistGV);
        btnsearch = (Button) view.findViewById(R.id.Search) ;
        btnAll = (Button) view.findViewById(R.id.btnAll) ;
        bntThem = (FloatingActionButton) view.findViewById(R.id.floatingActionButton) ;
        TextView txt=(TextView) view.findViewById(R.id.textViewHeader) ;
        sharedPreferencesGV = getActivity().getSharedPreferences("GiangVienShare", Context.MODE_PRIVATE);
        editorShare=sharedPreferencesGV.edit();
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, list) ;
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerKhoa.setAdapter(adapter);

                    }
                }
            }
        });
        listgv = new ArrayList<>();
        giangVienAdapter = new GiangVienAdapter(listgv);
        recyclerViewGv.setAdapter(giangVienAdapter);
        referenceGV.whereIn("chucDanh", Arrays.asList("Giảng viên", "Trưởng khoa")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    GiangVien s;
                    String giangvienref ;
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        s = new GiangVien(document.getString("emailGV"),document.getString("hoGV"),document.getString("tenGV"),document.getString("maKhoa"),document.getString("passwordGV"),document.getString("gioiTinhGV"),document.getDouble("salaryGV"),document.getString("hocVi"),document.getString("chucDanh"));
                        giangvienref=gson.toJson(s);
                        editorShare.putString("giangviens",giangvienref);
                        editorShare.commit();
                        listgv.add(s);
                    }
                    giangVienAdapter = new GiangVienAdapter(listgv);
                    recyclerViewGv.setAdapter(giangVienAdapter);
                }
            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenkhoa = spinnerKhoa.getSelectedItem().toString();
                Log.d("","===="+tenkhoa);
                listgv.clear();
                giangVienAdapter = new GiangVienAdapter(listgv);
                recyclerViewGv.setAdapter(giangVienAdapter);
                referenceGV.whereIn("chucDanh", Arrays.asList("Giảng viên", "Trưởng khoa")).whereEqualTo("maKhoa",tenkhoa).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {

                            //  referenceGV.whereEqualTo("maKhoa",tenkhoa).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            GiangVien s;
                            //   @Override
                            //  public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                s = new GiangVien(document.getString("emailGV"), document.getString("hoGV"), document.getString("tenGV"), document.getString("maKhoa"), document.getString("passwordGV"), document.getString("gioiTinhGV"), document.getDouble("salaryGV"), document.getString("hocVi"), document.getString("chucDanh"));
                                listgv.add(s);

                            }
                            giangVienAdapter = new GiangVienAdapter(listgv);
                            recyclerViewGv.setAdapter(giangVienAdapter);
                        }
                    }
                });

            }
        });

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listgv.clear();
                giangVienAdapter = new GiangVienAdapter(listgv);
                recyclerViewGv.setAdapter(giangVienAdapter);
                referenceGV.whereIn("chucDanh", Arrays.asList("Giảng viên", "Trưởng khoa")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    GiangVien s;
                    String giangvienref;
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        editorShare.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            s = new GiangVien(document.getString("emailGV"), document.getString("hoGV"), document.getString("tenGV"), document.getString("maKhoa"), document.getString("passwordGV"), document.getString("gioiTinhGV"), document.getDouble("salaryGV"), document.getString("hocVi"), document.getString("chucDanh"));
                            giangvienref=gson.toJson(s);
                            editorShare.putString("giangviens",giangvienref);
                            editorShare.commit();
                            listgv.add(s);

                        }
                        giangVienAdapter = new GiangVienAdapter(listgv);
                        recyclerViewGv.setAdapter(giangVienAdapter);
                    }
                });
                String giangvienref=sharedPreferencesGV.getString("giangviens",null);
                GiangVien s = gson.fromJson(giangvienref,GiangVien.class);
                if(s==null)
                    Toast.makeText(view.getContext(), "null", Toast.LENGTH_SHORT).show();
                else
                    Log.d("",s.getEmailGV());
            }

        });
        bntThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), SignUpTeacherActivity.class);
                startActivity(i);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewGv.setLayoutManager(linearLayoutManager);
        return view;
    }
}