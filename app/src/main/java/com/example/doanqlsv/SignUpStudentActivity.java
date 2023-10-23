package com.example.doanqlsv;

import static android.content.ContentValues.TAG;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.doanqlsv.Model.SinhVien;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SignUpStudentActivity extends AppCompatActivity {

    EditText mHoSv, mTenSv, mPassword, mNgaySinhSv, mQueQuan;
    Spinner mKhoa;
    RadioGroup mGioiTinh;
    Button mDangki;
    Spinner mLop;
    String lopsv;


    FirebaseFirestore firestore;

    int sott = 0; // Số thứ tự sinh viên
    public int getSott() {
        return sott;
    }

    public void setSott(int sott) {
        this.sott = sott;
    }
    String namTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student);

        mHoSv = findViewById(R.id.edtHoSv);
        mTenSv = findViewById(R.id.edtTenSv);
        mPassword = findViewById(R.id.edtPassword);
        mNgaySinhSv = findViewById(R.id.edtNgSinhSv);
        mQueQuan = findViewById(R.id.edtQueQuan);
        mGioiTinh = findViewById(R.id.gioiTinh);
        mDangki = findViewById(R.id.btnDangkySV);

        mKhoa = findViewById(R.id.spinnerKHOA);

        firestore = FirebaseFirestore.getInstance();
        final CollectionReference referenceSV = firestore.collection("SinhVien");
        final CollectionReference referenceKhoa = firestore.collection("Khoa");

        // Khởi tạo Spinner cho "Lớp" một lần và sử dụng nó sau này
       // ArrayAdapter<String> maLopAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
      //  maLopAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     //   mLop.setAdapter(maLopAdapter);

        // Just the year, with 2 digits
        namTN = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) % 100);
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUpStudentActivity.this, android.R.layout.simple_list_item_1, list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mKhoa.setAdapter(adapter);
                    }
                }
            }
        });
        //dem va gan ma sinh vien
        referenceSV.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            String k;
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshots = task.getResult();
                    for (QueryDocumentSnapshot doc : snapshots) {
                        sott++;
                    }

                    setSott(sott);
                    Log.d("********", String.valueOf(getSott()));
                }
            }
        });

        mNgaySinhSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        mDangki.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Lấy khoa được chọn
                String maKhoa = mKhoa.getSelectedItem().toString();

                // Tạo mã lớp mới dựa trên thông tin
                // Truy vấn Firestore để lấy số lượng sinh viên trong lớp hiện tại
                referenceSV.whereEqualTo("maLop", lopsv).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int slSV1lop = task.getResult().size();

                        // Kiểm tra xem lớp hiện tại đã đủ 30 sinh viên chưa
                        if (slSV1lop >= 30) {
                            // Nếu đã đủ 30 sinh viên, tạo mã lớp mới
                            int maLopSo = Integer.parseInt(maLopMoi); // Lấy số cuối cùng của mã lớp
                            maLopSo++; // Tăng số lớp lên 1
                            maLopMoi = String.format("%02d", maLopSo); // Định dạng lại mã lớp để có 2 chữ số (01, 02, ...)
                        }

                        // Sau khi tạo xong mã lớp mới, bạn có thể sử dụng nó trong `loplsv`
                        lopsv = maKhoa + namTN + maLopMoi; // Đây là mã lớp mới

                        // Chuyển họ tên thành chữ cái đầu duy nhất viết hoa
                        String hoSvFormatted = chuyenTenSv(mHoSv.getText().toString());
                        String tenSvFormatted = chuyenTenSv(mTenSv.getText().toString());

                        // Tạo đối tượng SinhVien
                        SinhVien item = new SinhVien();
                        int mssv = getSott();
                        setSott(mssv+1);
                        item.setMaSV(namTN + "5" + maKhoa + String.format("%03d", mssv));
                        item.setHoSV(hoSvFormatted);
                        item.setTenSV(tenSvFormatted);
                        item.setMaKhoa(maKhoa);
                        item.setMaLop(lopsv);
                        item.setNgaysinhSV(mNgaySinhSv.getText().toString());

                        item.setPasswordSV(mPassword.getText().toString());
                        item.setQuequanSV(mQueQuan.getText().toString());
                        int check = mGioiTinh.getCheckedRadioButtonId();
                        String gt = (check == R.id.radionam) ? "Nam" : "Nữ";
                        item.setGioitinhSV(gt);

                        // Chuyển đổi tên sinh viên thành viết thường và loại bỏ dấu
                        String tenSvWithoutAccents = chuyenChu(mTenSv.getText().toString());
                        String email = namTN + "5" + maKhoa + String.format("%03d", mssv) + tenSvWithoutAccents.toLowerCase().replaceAll("[^a-zA-Z0-9]", "") + "@ou.edu.vn";
                        Log.d("***", email + item.getMaSV());
                        item.setEmailSV(email);

                        // Thêm sinh viên vào Firestore
                        referenceSV.add(item)
                                .addOnSuccessListener(documentReference -> Toast.makeText(SignUpStudentActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(SignUpStudentActivity.this, "Thêm thất bại!", Toast.LENGTH_SHORT).show());
                    } else {
                        // Xử lý lỗi nếu có
                        Log.w(TAG, "Lỗi truy vấn số lượng sinh viên trong lớp", task.getException());
                        // Có thể hiển thị thông báo lỗi cho người dùng tại đây
                    }
                });
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                SignUpStudentActivity.this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Ngày tháng được chọn từ DatePickerDialog
                    String selectedDate = String.format(Locale.US, "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                    mNgaySinhSv.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR), // Năm mặc định
                calendar.get(Calendar.MONTH), // Tháng mặc định
                calendar.get(Calendar.DAY_OF_MONTH) // Ngày mặc định
        );

        datePickerDialog.show();
    }
    private String maLopMoi = "01"; // Giá trị mặc định ban đầu
    public String chuyenChu(String tenSV) {
        // Loại bỏ dấu và chuyển đổi thành chữ thường
        String chuThuong = Normalizer.normalize(tenSV, Normalizer.Form.NFD);
        String lowercaseWithoutAccents = chuThuong.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

        // Chuyển chữ cái đầu tiên thành chữ thường
        if (!lowercaseWithoutAccents.isEmpty()) {
            lowercaseWithoutAccents = Character.toLowerCase(lowercaseWithoutAccents.charAt(0)) + lowercaseWithoutAccents.substring(1);
        }
        return lowercaseWithoutAccents;
    }

    public String chuyenTenSv(String hoTen) {
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
