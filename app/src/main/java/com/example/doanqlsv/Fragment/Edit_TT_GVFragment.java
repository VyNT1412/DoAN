package com.example.doanqlsv.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanqlsv.Model.GiangVien;
import com.example.doanqlsv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Edit_TT_GVFragment extends Fragment {
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public final CollectionReference referenceGV = firestore.collection("GiaoVien");
    Button mUpdateGV,mback;
    Spinner mHV, mCD, mKhoa;
    TextView txtCd, txtHocVi, txtkhoa,hoten,gioitinh;
    ImageView img;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit__tt__gv, container, false);
        mUpdateGV = (Button) view.findViewById(R.id.btnCapNhatGV);
        mback = (Button) view.findViewById(R.id.btnBack);
        mHV = (Spinner) view.findViewById(R.id.spnHocVi) ;
        mKhoa = (Spinner) view.findViewById(R.id.spnKhoa) ;
        mCD = (Spinner) view.findViewById(R.id.spnCd) ;
        txtCd = (TextView) view.findViewById(R.id.ttcd);
        txtHocVi=(TextView) view.findViewById(R.id.tthv);
        txtkhoa=(TextView) view.findViewById(R.id.ttkhoa);
        hoten=(TextView) view.findViewById(R.id.tthoten);
        gioitinh=(TextView) view.findViewById(R.id.ttgioitinh);
        //

        //
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
                        mKhoa.setAdapter(adapter);
                    }
                }
            }
        });
        //
        String []listChucDanh = {"Giảng viên","Trưởng khoa"};
        String listHocVi[] = {"Thạc sĩ","Tiến sĩ","Nghiên cứu sinh"};
        ///
        ArrayAdapter chucDanhAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, listChucDanh);
        chucDanhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCD.setAdapter(chucDanhAdapter);
        ///
        ArrayAdapter<String> hocViAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, listHocVi);
        chucDanhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHV.setAdapter(hocViAdapter);
        //
        Bundle bundle = this.getArguments();
        GiangVien s = bundle.getParcelable("key");
        if(bundle != null){
            Log.d("",s.getPasswordGV()+s.getEmailGV());
            txtCd.setText(s.getChucDanh());
            txtkhoa.setText(s.getMaKhoa());
            txtHocVi.setText(s.getHocVi());
            hoten.setText(s.getHoGV()+" "+s.getTenGV());

            referenceGV.whereEqualTo("emailGV",s.getEmailGV()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                   String s = queryDocumentSnapshots.getDocuments().get(0).getString("gioitinhGV");
                    gioitinh.setText(s);
                }
            });

            Update(s);
            mback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Back();
                }
            });
        }
        return  view;
    }
    private void Update(GiangVien s)
    {
        mUpdateGV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String khoa = mKhoa.getSelectedItem().toString();
                String hocvi = mHV.getSelectedItem().toString();
                String cd = mCD.getSelectedItem().toString();
                double luong ;
                        Map<String, Object> gv = new HashMap<>();
                        gv.put("maKhoa", khoa);
                        gv.put("hocVi",hocvi);
                        gv.put("chucDanh",cd);
                        if(cd.equals("Giảng viên"))
                        {
                            luong = 8000000;
                            gv.put("salaryGV",luong);
                        }
                      if(cd.equals("Trưởng khoa"))
                        {
                             luong = 12000000;
                             gv.put("salaryGV",luong);
                         }
                        referenceGV.whereEqualTo("emailGV", s.getEmailGV()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                    String documentID = documentSnapshot.getId();
                                    referenceGV.document(documentID).update(gv).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(v.getContext(), "Thay doi thong tin thanh cong", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(v.getContext(), "That bai", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    txtCd.setText(s.getChucDanh());
                                    txtkhoa.setText(s.getMaKhoa());
                                    txtHocVi.setText(s.getHocVi());
                                }
                            }
                        });

            }
        });

    }
    private void Back()
    {
        AppCompatActivity activity = (AppCompatActivity) getView().getContext();
        Fragment_ListGV f =new Fragment_ListGV();


        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_ad,f).addToBackStack(null).commit();
    }
}