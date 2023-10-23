package com.example.doanqlsv;

import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.doanqlsv.Model.GiangVien;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class SignUpTeacherActivity extends AppCompatActivity {
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    final CollectionReference referenceGV = firestore.collection("GiaoVien");
    final CollectionReference referenceKhoa = firestore.collection("Khoa");
    String chucdanh, hocvi;
    EditText mHoGv, mTenGv, mPasswordGv ;
    TextView mLuong;
    RadioGroup mGioiTinhGv;
    Button mDangki;
    Spinner mKhoa, mChucDanh, mHocVi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_teacher);
        getData();
        getMaKhoa();
        String []listChucDanh = {"Giảng viên","Trưởng khoa","Nhân viên IT"};
        String listHocVi[] = {"Thạc sĩ","Tiến sĩ","Nghiên cứu sinh"};
        ///
        ArrayAdapter chucDanhAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listChucDanh);
        chucDanhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mChucDanh.setAdapter(chucDanhAdapter);
        ///
        ArrayAdapter<String> hocViAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listHocVi);
        hocViAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHocVi.setAdapter(hocViAdapter);
        //
        mDangki.setOnClickListener(new View.OnClickListener() {
            String luong;
            int counter = 1;
            @Override
            public void onClick(View v) {
                String maKhoa = mKhoa.getSelectedItem().toString();

                // Chuyển họ tên thành chữ cái đầu duy nhất viết hoa
                String hoGvFormatted = chuyenHoTenGv(mHoGv.getText().toString());
                String tenGvFormatted = chuyenHoTenGv(mTenGv.getText().toString());
                String chuCaiHo = layChuCaiDauCuaHo(mHoGv.getText().toString());
                String tenKhongDau = chuyenChu(mTenGv.getText().toString());
                // Tạo mã lớp mới dựa trên thông tin

                // Truy vấn Firestore để lấy số lượng sinh viên trong lớp hiện tại


                        // Tạo đối tượng GiangVien
                        GiangVien item = new GiangVien();
                        chucdanh = mChucDanh.getSelectedItem().toString();
                        hocvi = mHocVi.getSelectedItem().toString();

                        String email = tenKhongDau + "." + chuCaiHo + String.format("%02d", counter) + "@ou.edu.vn";
                        item.setEmailGV(email);
                        counter ++;
                        item.setHoGV(hoGvFormatted);
                        item.setTenGV(tenGvFormatted);
                        item.setHocVi(hocvi);
                        item.setMaKhoa(maKhoa);
                        item.setPasswordGV(mPasswordGv.getText().toString());
                        if(chucdanh.equals("Giảng viên"))
                            item.setSalaryGV(5500000);
                        if(chucdanh.equals("Trưởng khoa"))
                            item.setSalaryGV(8000000);
                        if(chucdanh.equals("Nhân viên IT"))
                            item.setSalaryGV(12000000);

                        Log.d("+++++", String.valueOf(item.getSalaryGV()));
                        item.setTenGV(mTenGv.getText().toString());
                        int check = mGioiTinhGv.getCheckedRadioButtonId();
                        item.setChucDanh(chucdanh);
                        String gt = (check == R.id.radionam) ? "Nam" : "Nữ";
                        item.setGioiTinhGV(gt);

                        // Thêm giảng viên vào Firestore
                        referenceGV.add(item)
                                .addOnSuccessListener(documentReference -> {Toast.makeText(SignUpTeacherActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                    Intent i =new Intent(SignUpTeacherActivity.this,Admin_MainActivity.class);
                                    startActivity(i);
                                })
                                .addOnFailureListener(e -> Toast.makeText(SignUpTeacherActivity.this, "Thêm thất bại!", Toast.LENGTH_SHORT).show());


            }
        });

    }
    private void getData()
    {
        mHoGv =  findViewById(R.id.edtHoGv);
        mTenGv = findViewById(R.id.edtTenGv);
        mPasswordGv =  findViewById(R.id.edtPasswordGv);
        mLuong = findViewById(R.id.edtLuong);
        mGioiTinhGv =  findViewById(R.id.gioiTinhGv);
        mKhoa = findViewById(R.id.spinnerKHOA);
        mChucDanh =  findViewById(R.id.spinnerChucDanh);
        mHocVi =  findViewById(R.id.spinnerHocVi);
        mDangki =  findViewById(R.id.btnDangkyGV);
    }
    private void getMaKhoa()
    {
        referenceKhoa.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUpTeacherActivity.this, android.R.layout.simple_list_item_1, list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mKhoa.setAdapter(adapter);
                    }
                }
            }
        });
    }

    public String chuyenChu(String tenGV) {
        // Loại bỏ dấu và chuyển đổi thành chữ thường
        String chuThuong = Normalizer.normalize(tenGV, Normalizer.Form.NFD);
        String lowercaseWithoutAccents = chuThuong.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

        // Chuyển chữ cái đầu tiên thành chữ thường
        if (!lowercaseWithoutAccents.isEmpty()) {
            lowercaseWithoutAccents = Character.toLowerCase(lowercaseWithoutAccents.charAt(0)) + lowercaseWithoutAccents.substring(1);
        }
        return lowercaseWithoutAccents;
    }
    public String layChuCaiDauCuaHo(String hoGV) {
        // Tách tên thành các từ
        String[] tuKhoa = hoGV.split(" ");

        StringBuilder chucaidau = new StringBuilder();

        // Duyệt qua các từ và lấy chữ cái đầu tiên của mỗi từ
        for (String tu : tuKhoa) {
            if (!tu.isEmpty()) {
                char chucaidauTu = Character.toLowerCase(tu.charAt(0));
                chucaidau.append(chucaidauTu);
            }
        }
        return chucaidau.toString();
    }

    public String chuyenHoTenGv(String hoTen) {
        StringBuilder result = new StringBuilder();

        if (hoTen != null && !hoTen.isEmpty()) {
            String[] parts = hoTen.split("\\s+");
            for (String part : parts) {
                if (!part.isEmpty()) {
                    // Chuyển tất cả chữ cái thành chữ thường
                    String lowercasePart = part.toLowerCase();
                    // Chuyển chữ cái đầu của mỗi từ thành chữ hoa và thêm vào kết quả
                    String formattedPart = Character.toUpperCase(lowercasePart.charAt(0)) + lowercasePart.substring(1);
                    result.append(formattedPart).append(" ");
                }
            }
            // Loại bỏ dấu space cuối cùng nếu có
            if (result.length() > 0) {
                result.setLength(result.length() - 1);
            }
        }
        return result.toString();
    }
}