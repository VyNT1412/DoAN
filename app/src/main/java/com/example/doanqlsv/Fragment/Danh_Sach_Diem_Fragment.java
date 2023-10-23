package com.example.doanqlsv.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doanqlsv.Adapter.DiemAdapter;
import com.example.doanqlsv.Model.Diem;
import com.example.doanqlsv.Model.SinhVien;
import com.example.doanqlsv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Danh_Sach_Diem_Fragment extends Fragment {

   Spinner spnHK, spnMon;
   ListView listds;
   ArrayList<Diem> list;
   Button loc,chonlop;
   FirebaseFirestore firestore = FirebaseFirestore.getInstance();
   CollectionReference reference = firestore.collection("Diem");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_danh__sach__diem, container, false);
        init(view);

        loadHocKi(view);
        loadDiem(view);
        Loc(view);
        return view;
    }


    private void loadHocKi(View view)
    {
        firestore.collection("HocKy").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    QuerySnapshot snapshot = task.getResult();
                    if (snapshot != null) {
                        List<Integer> list = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : snapshot) {
                            int maKhoa = doc.get("maHK", Integer.class);
                            list.add(maKhoa);
                        }
                        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnHK.setAdapter(adapter);
                    }
                }
            }
        });
    }
    private void loadDiem(View view)
    {
        chonlop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(spnHK.getSelectedItem().toString());
                firestore.collection("Diem").whereEqualTo("maHK",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.getResult().isEmpty())
                        {
                            Toast.makeText(getContext(), "Hoc ki này điểm chưa có hoặc chưa kết thúc" , Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(task.isSuccessful())
                        {
                            List<String> list = new ArrayList<>();
                            QuerySnapshot snapshot = task.getResult();
                            for (QueryDocumentSnapshot doc : snapshot) {
                                String mahk = doc.getString("maMon");
                                list.add(mahk);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, list);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnMon.setAdapter(adapter);
                        }

                    }
                });
            }
        });
    }
    private void Loc(View view)
    {
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(spnHK.getSelectedItem().toString());
                String mamon = spnMon.getSelectedItem().toString();
                firestore.collection("Diem").whereEqualTo("maHK",id).whereEqualTo("maMon",mamon).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    Diem d;
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            List<Diem> listD = new ArrayList<>();
                            QuerySnapshot snapshot = task.getResult();
                            for (QueryDocumentSnapshot doc : snapshot) {
                                String mamon = doc.getString("maMon");
                                double diemGk = doc.get("diemGK",Double.class);
                                double diemCk=doc.getDouble("diemCK");
                                String mssv = doc.getString("maSV");
                                String emailGv = doc.getString("emailGV");
                                d = new Diem(diemCk,diemGk,emailGv,id,mssv,mamon);
                                listD.add(d);
                            }

                            DiemAdapter diemAdapter = new DiemAdapter((ArrayList<Diem>) listD,view.getContext(), android.R.layout.simple_list_item_1);
                            listds.setAdapter(diemAdapter);
                        }

                    }
                });
            }
        });
    }
    private void init(View view)
    {
        spnHK = (Spinner) view.findViewById(R.id.spinnerHocKi);
        spnMon = (Spinner) view.findViewById(R.id.spinnertheoMon);
        listds = (ListView) view.findViewById(R.id.ListDiem);
        chonlop  = (Button) view.findViewById(R.id.btnChonMon);
        loc = (Button) view.findViewById(R.id.btnLoc);
    }

}