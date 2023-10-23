package com.example.doanqlsv.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.doanqlsv.MainActivity_SinhVien;
import com.example.doanqlsv.Model.SinhVien;
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


public class ChangePasswordSv_Fragment extends Fragment {
    private  View view;

    private MainActivity_SinhVien mainActivity_sinhVien;
   private TextView oldpass, newpass, confpass;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public final CollectionReference referenceSV = firestore.collection("SinhVien");

    private   Button mDoiMk;
    String sinhvienID_doc;
    SinhVien s;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull  ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_password_sv_, container, false);
        mainActivity_sinhVien = (MainActivity_SinhVien) getActivity();

        initUI();
        sinhvienID_doc = mainActivity_sinhVien.getDocumentIDSV();
        s = mainActivity_sinhVien.getS();
        Log.d("","+++888"+s.getMaSV());
        Log.d("","+++888"+s.getHoSV());
        Log.d("","+++888"+s.getTenSV());
        Log.d("","+++888"+s.getMaKhoa());

        DoiMatKhau();
        return view;
    }

    private void initUI() {
        oldpass = (TextView) view.findViewById(R.id.edtMkcu);
        newpass = (TextView) view.findViewById(R.id.edtMkMoi);
        confpass = (TextView) view.findViewById(R.id.edtMkMoiConfess);
        mDoiMk = (Button) view.findViewById(R.id.btnDoiMk);



    }

    private void DoiMatKhau()
    {
        mDoiMk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String  matKhauCu=oldpass.getText().toString(),
                        matKhauMoi=newpass.getText().toString(),
                        xacNhanMatKhau=confpass.getText().toString();
                updatePasswordSV(matKhauCu,matKhauMoi,xacNhanMatKhau);

            }


        });
    }
    private void updatePasswordSV(String matKhauCu, String matKhauMoi, String xacNhanMatKhau) {
      if(matKhauMoi.equals(xacNhanMatKhau))
      {
          Map<String,Object> passchange = new HashMap<>();
          passchange.put("passwordSV",matKhauMoi);
          referenceSV.whereEqualTo("maSV",sinhvienID_doc).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  if(task.isSuccessful()&&!task.getResult().isEmpty())
                  {
                      DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                      String documentID = documentSnapshot.getId();
                      referenceSV.document(documentID).update(passchange).addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void unused) {
                              Toast.makeText(mainActivity_sinhVien, "Đổi password thành công", Toast.LENGTH_SHORT).show();                          }
                      }).addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                              Toast.makeText(mainActivity_sinhVien, "Đổi password không thành công", Toast.LENGTH_SHORT).show();
                          }
                      });
                  }
              }
          });
      }
    }
}