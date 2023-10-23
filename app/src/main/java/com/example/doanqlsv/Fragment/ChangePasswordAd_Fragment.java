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

import com.example.doanqlsv.Admin_MainActivity;
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


public class ChangePasswordAd_Fragment extends Fragment {

    private View view;
    private TextView oldpass, newpass, confpass;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public final CollectionReference referenceGV = firestore.collection("GiaoVien");
    private Admin_MainActivity admin_mainActivity;
    private Button mDoiMkAd;
    String admin_ID_doc;
    GiangVien s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_change_password_ad, container, false);
        admin_mainActivity = (Admin_MainActivity) getActivity();
        s = admin_mainActivity.getGv();
        initUI();
        mDoiMkAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  matKhauCu=oldpass.getText().toString(),
                        matKhauMoi=newpass.getText().toString(),
                        xacNhanMatKhau=confpass.getText().toString();
                updatePasswordSV(matKhauCu,matKhauMoi,xacNhanMatKhau);

            }


        });
        return  view;
    }
    private void initUI() {
        oldpass = (TextView) view.findViewById(R.id.edtMkcuAd);
        newpass = (TextView) view.findViewById(R.id.edtMkMoiAd);
        confpass = (TextView) view.findViewById(R.id.edtMkMoiConfessAd);
        mDoiMkAd = (Button) view.findViewById(R.id.btnDoiMkAd);

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
                                    Toast.makeText(admin_mainActivity, "Đổi password thành công", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(admin_mainActivity, "Đổi password khng thành công", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        }
    }
}