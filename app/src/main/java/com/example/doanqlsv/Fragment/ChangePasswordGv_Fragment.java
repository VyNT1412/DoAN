package com.example.doanqlsv.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanqlsv.MainActivity_GiangVien;
import com.example.doanqlsv.Model.GiangVien;
import com.example.doanqlsv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class ChangePasswordGv_Fragment extends Fragment {

    private  View view;

    private MainActivity_GiangVien mainActivity_giangVien;
    private TextView oldpass, newpass, confpass;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public final CollectionReference referenceGV = firestore.collection("GiaoVien");

    private Button mDoiMkGV;
    String giaovienID_doc;
    GiangVien s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_password_gv, container, false);
        mainActivity_giangVien = (MainActivity_GiangVien) getActivity();
        giaovienID_doc = mainActivity_giangVien.getDocumentIDGV();
        s = mainActivity_giangVien.getGv();
        initUI();
        mDoiMkGV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  matKhauCu=oldpass.getText().toString(),
                        matKhauMoi=newpass.getText().toString(),
                        xacNhanMatKhau=confpass.getText().toString();
                updatePasswordSV(matKhauCu,matKhauMoi,xacNhanMatKhau);

            }


        });
        // Inflate the layout for this fragment
        return view;
    }
    private void initUI() {
        oldpass = (TextView) view.findViewById(R.id.edtMkcuGV);
        newpass = (TextView) view.findViewById(R.id.edtMkMoiGV);
        confpass = (TextView) view.findViewById(R.id.edtMkMoiConfessGV);
        mDoiMkGV = (Button) view.findViewById(R.id.btnDoiMkGV);



    }


    private void updatePasswordSV(String matKhauCu, String matKhauMoi, String xacNhanMatKhau) {
        if (matKhauCu.equals(s.getPasswordGV())) {
            if (matKhauMoi.equals(xacNhanMatKhau)) {
                Map<String, Object> passchange = new HashMap<>();
                passchange.put("passwordGV", matKhauMoi);
                referenceGV.whereEqualTo("emailGV", s.getEmailGV()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            referenceGV.document(documentID).update(passchange).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(mainActivity_giangVien, "Đổi password thành công", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(mainActivity_giangVien, "Đổi password không thành công", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        }
    }
}
