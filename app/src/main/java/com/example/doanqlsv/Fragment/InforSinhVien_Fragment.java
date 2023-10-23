package com.example.doanqlsv.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.doanqlsv.MainActivity_SinhVien;
import com.example.doanqlsv.Model.SinhVien;
import com.example.doanqlsv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class InforSinhVien_Fragment extends Fragment {

    ImageView Sphoto;
    TextView hoten, gt, khoa, lop, ns, qq, mssv, email;
    private  View view;
    private MainActivity_SinhVien mainActivity_sinhVien;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public final CollectionReference referenceSV = firestore.collection("SinhVien");
    SinhVien s;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull  ViewGroup container,@NonNull
    Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_infor_sinh_vien_, container, false);
        mainActivity_sinhVien = (MainActivity_SinhVien) getActivity();
        Unit();

        s = mainActivity_sinhVien.getS();
        mssv.setText(s.getMaSV());
        email.setText(s.getEmailSV());
        hoten.setText(s.getHoSV()+" "+s.getTenSV());
        lop.setText(s.getMaLop());
        qq.setText(s.getQuequanSV());
        gt.setText(s.getGioitinhSV());
        ns.setText(s.getNgaysinhSV());
        if (s != null) {
            if ("Nam".equalsIgnoreCase(s.getGioitinhSV())) {
                // Set the male avatar image
                Sphoto.setImageResource(R.drawable.man_ava);
            } else if ("Nữ".equalsIgnoreCase(s.getGioitinhSV())) {
                // Set the female avatar image
                Sphoto.setImageResource(R.drawable.woman_ava);
            }
        }
        //
        firestore.collection("Khoa").whereEqualTo("maKhoa",s.getMaKhoa()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()&&!task.getResult().isEmpty())
                {
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentID = documentSnapshot.getString("tenKhoa");
                    khoa.setText(documentID);
                }
            }
        });
        return view;

    }
    private void Unit()
    {
        mainActivity_sinhVien = (MainActivity_SinhVien) getActivity();
        hoten = (TextView) view.findViewById(R.id.htsv);
        lop=(TextView) view.findViewById(R.id.lop);
        khoa=(TextView) view.findViewById(R.id.khoa);
        gt=(TextView) view.findViewById(R.id.gt);
        qq=(TextView) view.findViewById(R.id.qq);
        ns=(TextView) view.findViewById(R.id.ns);
        mssv=(TextView) view.findViewById(R.id.massv);
        email=(TextView) view.findViewById(R.id.emailsv);
        Sphoto = view.findViewById(R.id.Sphoto);

    }
}