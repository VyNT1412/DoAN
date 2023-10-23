package com.example.doanqlsv;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CauHinh {
//
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
   public final CollectionReference referenceSV = firestore.collection("SinhVien");
    public final CollectionReference referenceKhoa = firestore.collection("Khoa");

    //

    public static final String SHARE_PREFERENCES = "Share_Preferences";
    public static final String KEY_SINHVIEN = "Key_sinhvien";
    public static final String KEY_PROFILE_SINHVIEN = "Key_profile_sinhvien";
}
