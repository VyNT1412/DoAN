package com.example.doanqlsv.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanqlsv.Fragment.Edit_TT_GVFragment;
import com.example.doanqlsv.Model.GiangVien;
import com.example.doanqlsv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GiangVienAdapter extends RecyclerView.Adapter<GiangVienAdapter.GiangVienViewHolder> {
    ArrayList<GiangVien> listgv;
    Context context;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public final CollectionReference referenceGV = firestore.collection("GiaoVien");
    public GiangVienAdapter(ArrayList<GiangVien> listgv)
    {
        this.listgv= listgv;
    }


    @NonNull
    @Override
    public GiangVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_gv,parent,false);
        GiangVienViewHolder viewHolder = new GiangVienViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GiangVienViewHolder holder, int position) {
        GiangVien item = listgv.get(position);
        int textColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.lightgrey);
//        holder.sttgv.setText("STT: " + item.getStt());
//        holder.sttgv.setTextColor(textColor);


        holder.hogv.setText(item.getHoGV());
        holder.hogv.setTextColor(textColor);

        holder.tengv.setText(item.getTenGV());
        holder.tengv.setTextColor(textColor);

        holder.khoagv.setText(item.getMaKhoa());
        String tk = "";
        if(item.getMaKhoa().equals("LW"))
            tk="Luật";
        else if(item.getMaKhoa().equals("AD"))
            tk="Kinh tế";
        else if(item.getMaKhoa().equals("IT"))
            tk="CNTT";
        else if(item.getMaKhoa().equals("BI"))
            tk="Công Nghệ Sinh Học";
        else if(item.getMaKhoa().equals("NN"))
            tk="Ngôn Ngữ";
        else if(item.getMaKhoa().equals("BD"))
            tk="Xây Dựng";
        else if(item.getMaKhoa().equals("FI"))
            tk="Tài Chính Ngân Hàng";
        holder.khoagv.setText(tk);
        holder.khoagv.setTextColor(textColor);

        holder.emailgv.setText(item.getEmailGV());
        holder.emailgv.setTextColor(textColor);

        holder.hocvi.setText(item.getHocVi());
        holder.hocvi.setTextColor(textColor);

        holder.chucdanh.setText(item.getChucDanh());
        holder.chucdanh.setTextColor(textColor);

        double salary = item.getSalaryGV();
        String formattedSalary = String.format("%.0f", salary); // Định dạng số tiền
        try {
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US); // Sử dụng Locale.US để có dấu chấm ngăn cách hàng nghìn
            formattedSalary = numberFormat.format(Double.parseDouble(formattedSalary));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String displayedSalary = formattedSalary + " VND"; // Thêm đơn vị tiền tệ (nếu cần)
        holder.luong.setText(displayedSalary);
        holder.luong.setTextColor(textColor);


        holder.editgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("key",item);
                Log.d("",item.getEmailGV());
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Edit_TT_GVFragment f =new Edit_TT_GVFragment();
                f.setArguments(bundle);

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_ad,f).addToBackStack(null).commit();

            }
        });
        holder.deletegv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(v.getContext());
                b.setTitle("Xóa giảng viên").setMessage("Bạn chắc chắn muốn xóa?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        referenceGV.whereEqualTo("emailGV",item.getEmailGV()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                    String id = documentSnapshot.getId();
                                    referenceGV.document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        b.setCancelable(true);
                    }
                }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listgv.size();
    }

    class GiangVienViewHolder extends RecyclerView.ViewHolder{
        //ImageView imgGT;
        TextView sttgv,hogv,tengv,emailgv,luong,chucdanh,hocvi,khoagv;
        Button editgv, deletegv;
        public GiangVienViewHolder(@NonNull View view) {

            super(view);
            hogv= (TextView) view.findViewById(R.id.itemHoGv);
            tengv= (TextView) view.findViewById(R.id.itemTenGv);
            emailgv= (TextView) view.findViewById(R.id.itemEmail);
            luong= (TextView) view.findViewById(R.id.itemLuong);
            chucdanh= (TextView) view.findViewById(R.id.itemChucDanh);
            hocvi= (TextView) view.findViewById(R.id.itemHocVi);
            khoagv= (TextView) view.findViewById(R.id.itemKhoa);
            editgv=(Button) view.findViewById(R.id.btnSuaTTGv);
            deletegv=(Button)  view.findViewById(R.id.btnXoaTTGv);

        }
    }
}