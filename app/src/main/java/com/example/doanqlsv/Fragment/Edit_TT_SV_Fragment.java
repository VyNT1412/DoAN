package com.example.doanqlsv.Fragment;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanqlsv.Adapter.SinhVienAdapter;
import com.example.doanqlsv.Model.SinhVien;
import com.example.doanqlsv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Edit_TT_SV_Fragment extends Fragment {

    TextView txthotensv, txtemailsv, txtkhoasv,txttenkhoasv,txtmalopsv,txtgt,txtngaysinhsv,txtqq;
    Button editsv, backmain,chonkhoa;
    Spinner spnkhoa,spnlop;
    ImageView imggt;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public final CollectionReference referenceSV = firestore.collection("SinhVien");

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit__sv, container, false);

        txthotensv = view.findViewById(R.id.tthotensv);
        txtkhoasv=view.findViewById(R.id.ttmalopsv);
        txtemailsv=view.findViewById(R.id.ttemailsv);
        txttenkhoasv=view.findViewById(R.id.tttenkhoasv);
        txtmalopsv=view.findViewById(R.id.ttmalopsv);
        txtgt=view.findViewById(R.id.ttgioitinhsv);
        txtngaysinhsv=view.findViewById(R.id.ttngaysinhsv);
        txtqq=view.findViewById(R.id.ttquequansv);
        editsv=view.findViewById(R.id.btnCapNhatGV);
        chonkhoa=view.findViewById(R.id.btnchonkhoa);
        backmain=view.findViewById(R.id.btnBackDs);
        spnkhoa=view.findViewById(R.id.spnmakhoa);
        spnlop=view.findViewById(R.id.spnmalop);
        imggt=view.findViewById(R.id.imageViewGTSV);

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
                        spnkhoa.setAdapter(adapter);
                    }

                }
            }
        });
        //

        Bundle bundle = this.getArguments();
        SinhVien s = bundle.getParcelable("keySV");
        //
        if(bundle != null){
            Log.d("",s.getHoSV()+s.getTenSV()+"**"+s.getEmailSV());
            txthotensv.setText(s.getHoSV()+" "+s.getTenSV());
            txtemailsv.setText(s.getEmailSV());
            txtkhoasv.setText(s.getMaKhoa());
            txtgt.setText(s.getGioitinhSV());
            if(s.getMaKhoa().equals("LW"))
               txttenkhoasv.setText( "Luật");
            else if(s.getMaKhoa().equals("AD"))
                txttenkhoasv.setText( "Kinh tế");
            else if(s.getMaKhoa().equals("IT"))
                txttenkhoasv.setText( "CNTT");
            else if(s.getMaKhoa().equals("BI"))
                txttenkhoasv.setText( "CN Sinh Học");
            else if(s.getMaKhoa().equals("NN"))
                txttenkhoasv.setText( "Ngôn Ngữ");
            else if(s.getMaKhoa().equals("BD"))
                txttenkhoasv.setText( "Xây Dựng");
            else if(s.getMaKhoa().equals("FI"))
                txttenkhoasv.setText( "Tài Chính Ngân Hàng");
            txtngaysinhsv.setText(s.getNgaysinhSV());
            txtmalopsv.setText(s.getMaLop());
            txtqq.setText(s.getQuequanSV());
            ChonKhoa(s);
            Update(s);
            backmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Back();
                }
            });
        }
        return view;
    }
    private void ChonKhoa(SinhVien s)
    {
        chonkhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = spnkhoa.getSelectedItem().toString();
                referenceSV.whereEqualTo("maKhoa",id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    SinhVien s;
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<String> list= new ArrayList<String>();
                        firestore.collection("Lop").whereEqualTo("maKhoa",id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            SinhVien s;
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<String> list= new ArrayList<String>();
                                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                    String malop = document.getString("maLop");
                                    list.add(malop);
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, list);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnlop.setAdapter(adapter);
                            }
                        });
                    }
                });
            }
        });
    }
    private void Update(SinhVien s)
    {
        editsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String khoa = spnkhoa.getSelectedItem().toString();
                String malop = spnlop.getSelectedItem().toString();
                String c1 = s.getEmailSV().substring(0,3);
                String c2 = s.getEmailSV().substring(5,s.getEmailSV().length());
                String emailmoi=c1+khoa+c2;
                String mssvmoi=c1+khoa+s.getEmailSV().substring(5,8);
                Map<String, Object> sv = new HashMap<>();
                Log.d("",emailmoi+"++++++"+mssvmoi);
                sv.put("maKhoa", khoa);
                sv.put("maLop",malop);

                referenceSV.whereEqualTo("emailSV", s.getEmailSV()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            referenceSV.document(documentID).update(sv).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("",s.getEmailSV());
                                    Toast.makeText(v.getContext(), "Thay doi thong tin thanh cong", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(v.getContext(), "That bai", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        if(s.getMaKhoa().equals("LW"))
                            txttenkhoasv.setText( "Luật");
                        else if(s.getMaKhoa().equals("AD"))
                            txttenkhoasv.setText( "Kinh tế");
                        else if(s.getMaKhoa().equals("IT"))
                            txttenkhoasv.setText( "CNTT");
                        else if(s.getMaKhoa().equals("BI"))
                            txttenkhoasv.setText( "CN Sinh Học");
                        else if(s.getMaKhoa().equals("NN"))
                            txttenkhoasv.setText( "Ngôn Ngữ");
                        else if(s.getMaKhoa().equals("BD"))
                            txttenkhoasv.setText( "Xây Dựng");
                        else if(s.getMaKhoa().equals("FI"))
                            txttenkhoasv.setText( "Tài Chính Ngân Hàng");
                        txtemailsv.setText(s.getEmailSV());
                        txtmalopsv.setText(s.getMaLop());
                    }
                });
                AppCompatActivity activity = (AppCompatActivity) getView().getContext();
                Fragment_ListSV f =new Fragment_ListSV() ;


                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_ad,f).addToBackStack(null).commit();
            }
        });
    }
    private void Back()
    {
        AppCompatActivity activity = (AppCompatActivity) getView().getContext();
        Fragment_ListSV f =new Fragment_ListSV() ;


        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_ad,f).addToBackStack(null).commit();
    }
}