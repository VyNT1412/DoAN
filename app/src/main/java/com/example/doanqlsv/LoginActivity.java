package com.example.doanqlsv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanqlsv.Model.GiangVien;
import com.example.doanqlsv.Model.SinhVien;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    EditText mtendangnhap;

    EditText mMatKhau;
    RadioGroup mrole;
    Button mDangNhap;
    String tendangnhap, pass;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference sinhvienRef = db.collection("SinhVien");
    CollectionReference giaovienRef = db.collection("GiaoVien");
    //bien file java thanh file xml
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getLoadData();
        pass = mMatKhau.getText().toString();

        mDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = mrole.getCheckedRadioButtonId();

                if (mtendangnhap.getText().toString().equals("") || mMatKhau.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Nhập đầy đủ thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    if (check == R.id.rdSinhVien) {
                        sinhvienRef.whereEqualTo("maSV", mtendangnhap.getText().toString()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                            String mssv;
                            String emailsv,hosv,tensv,makhoa,malop,gioitinh,ngaysinh,quequan;
                            SinhVien s;
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    Log.w("TAG", "Listen failed.", e);
                                    return;
                                }
                                for (QueryDocumentSnapshot doc : value) {

                                    mssv = doc.getString("maSV");
                                    pass = doc.getString("passwordSV");
                                    emailsv=doc.getString("emailSV");
                                    hosv=doc.getString("hoSV");
                                    tensv=doc.getString("tenSV");
                                    quequan=doc.getString("quequanSV");
                                    malop=doc.getString("maLop");
                                    makhoa=doc.getString("maKhoa");
                                    gioitinh=doc.getString("gioitinhSV");
                                    ngaysinh=doc.getString("ngaysinhSV");
                                    if (mMatKhau.getText().toString().equals(pass)) {
                                        s = new SinhVien(mssv,hosv,makhoa,tensv,pass,gioitinh,emailsv,malop,ngaysinh,quequan);
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(LoginActivity.this, MainActivity_SinhVien.class);
                                        i.putExtra("sinhvien",s);
                                        i.putExtra("documentID_SV",doc.getId());
                                        i.putExtra("mssv",mssv);
                                        startActivity(i);

                                        Log.d("TAG", "Current sinhvien in : " + mssv);
                                        break;
                                    }
                                    if (doc.get("maSV") == null) {

                                        Toast.makeText(LoginActivity.this, "Đăng nhập KHÔNG thành công", Toast.LENGTH_SHORT).show();

                                    }
                                }

                            }
                        });
                    }
                    if (check == R.id.rdGiangVien)
                    {

                        if (!mtendangnhap.getText().toString().contains("@ou.edu.vn")) {
                            Toast.makeText(LoginActivity.this, "Định dạng mail sai, vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                        } else {
                            giaovienRef.whereIn("chucDanh", Arrays.asList("Giảng viên", "Trưởng khoa")).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                String emailgv;
                                String chucdanh;
                                String gioitinhgv;
                                String hocvi,makhoagv,hogv,tengv;

                                double luong ;
                                GiangVien s;
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value,
                                                    @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {
                                        Log.w("TAG", "Listen failed.", e);
                                        return;
                                    }
                                    for (QueryDocumentSnapshot doc : value) {
                                        emailgv = doc.getString("emailGV");
                                        pass = doc.getString("passwordGV");
                                        chucdanh = doc.getString("chucDanh");
                                        luong=doc.getDouble("salaryGV");
                                        hogv=doc.getString("hoGV");
                                        hocvi=doc.getString("hocVi");
                                        makhoagv=doc.getString("maKhoa");
                                        tengv=doc.getString("tenGV");
                                        gioitinhgv=doc.getString("gioiTinhGV");
                                        s=new GiangVien(emailgv,hogv,tengv,makhoagv,pass,gioitinhgv,luong,hocvi,chucdanh);
                                        if (emailgv.equals(mtendangnhap.getText().toString())) {
                                            if (mMatKhau.getText().toString().equals(pass)) {

                                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(LoginActivity.this, MainActivity_GiangVien.class);
                                                i.putExtra("giangvien",s);
                                                i.putExtra("documentID_GV",doc.getId());
                                                startActivity(i);
                                                Log.d("TAG", "Current sinhvien in : " + emailgv);
                                                break;
                                            }
                                            if (doc.get("emailGV") == null) {
                                                Toast.makeText(LoginActivity.this, "Đăng nhập KHÔNG thành công", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                    }
                                }
                            });
                        }
                    }
                    if (check == R.id.rdAdmin)
                    {

                        mtendangnhap.setHint("Nhập mail đăng nhập");
                        if (!mtendangnhap.getText().toString().contains("@ou.edu.vn")) {
                            Toast.makeText(LoginActivity.this, "Định dạng mail sai, vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                        } else {
                            giaovienRef.whereNotIn("chucDanh", Arrays.asList("Giảng viên", "Trưởng khoa")).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                String emailgv;
                                String chucdanh;
                                String gioitinhgv;
                                String hocvi,makhoagv,hogv,tengv;
                                GiangVien s;
                                double luong ;
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value,
                                                    @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {
                                        Log.w("TAG", "Listen failed.", e);
                                        return;
                                    }
                                    for (QueryDocumentSnapshot doc : value) {

                                        emailgv = doc.getString("emailGV");
                                        pass = doc.getString("passwordGV");
                                        chucdanh = doc.getString("chucDanh");
                                        luong=doc.getDouble("salaryGV");
                                        hogv=doc.getString("hoGV");
                                        hocvi=doc.getString("hocVi");
                                        makhoagv=doc.getString("maKhoa");
                                        tengv=doc.getString("tenGV");
                                        gioitinhgv=doc.getString("gioiTinhGV");
                                        s=new GiangVien(emailgv,hogv,tengv,makhoagv,pass,gioitinhgv,luong,hocvi,chucdanh);
                                        if (emailgv.equals(mtendangnhap.getText().toString())) {
                                            if (mMatKhau.getText().toString().equals(pass)) {
                                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(LoginActivity.this, Admin_MainActivity.class);
                                                i.putExtra("admin",s);
                                                i.putExtra("documentID_ADMIN",doc.getId());
                                                startActivity(i);
                                                Log.d("TAG", "Current sinhvien in : " + emailgv);
                                                break;
                                            }
                                        }
                                        if (doc.get("emailGV") == null) {

                                            Toast.makeText(LoginActivity.this, "Đăng nhập KHÔNG thành công", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });

    }
    private void getLoadData()
    {
        mtendangnhap = (EditText) findViewById(R.id.edtUserName);
        mMatKhau = (EditText) findViewById(R.id.edtPass);
        mrole = (RadioGroup) findViewById(R.id.role);
        mDangNhap = (Button) findViewById(R.id.btnDangNhap);

    }
}


