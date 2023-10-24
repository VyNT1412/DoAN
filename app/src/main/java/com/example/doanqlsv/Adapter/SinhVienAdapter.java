package com.example.doanqlsv.Adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanqlsv.Fragment.Edit_TT_GVFragment;
import com.example.doanqlsv.Fragment.Edit_TT_SV_Fragment;
import com.example.doanqlsv.Model.SinhVien;
import com.example.doanqlsv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SinhVienAdapter  extends RecyclerView.Adapter<SinhVienAdapter.SinhVienViewHolder>{
    ArrayList<SinhVien> listsv;
    Context context;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public final CollectionReference referenceSV = firestore.collection("SinhVien");
    public SinhVienAdapter(ArrayList<SinhVien> listsv)
    {
        this.listsv=listsv;
    }
    @NonNull
    @Override
    public SinhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_sv,parent,false);
        SinhVienAdapter.SinhVienViewHolder viewHolder = new SinhVienViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SinhVienViewHolder holder, int position) {
        SinhVien item = listsv.get(position);
        holder.masv.setText(item.getMaSV());
        holder.hosv.setText(item.getHoSV());
        holder.tensv.setText(item.getTenSV());
        holder.emailsv.setText(item.getEmailSV());
        holder.malopsv.setText(item.getMaLop());
        holder.makhoasv.setText(item.getMaKhoa());
        String tk = "";
        if(item.getMaKhoa().equals("LW"))
            tk="Luật";
        else if(item.getMaKhoa().equals("AD"))
            tk="Kinh tế";
        else if(item.getMaKhoa().equals("IT"))
            tk="CNTT";
        else if(item.getMaKhoa().equals("BI"))
            tk="CN Sinh Học";
        else if(item.getMaKhoa().equals("NN"))
            tk="Ngôn Ngữ";
        else if(item.getMaKhoa().equals("BD"))
            tk="Xây Dựng";
        else if(item.getMaKhoa().equals("FI"))
            tk="Tài Chính Ngân Hàng";
        holder.tenkhoa.setText(tk);
        holder.editsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("keySV",item);
                Log.d("",item.getEmailSV());
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Edit_TT_SV_Fragment f =new Edit_TT_SV_Fragment();
                f.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_ad,f).addToBackStack(null).commit();

            }
        });
        holder.deletesv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(v.getContext());
                b.setTitle("Xóa giảng viên").setMessage("Bạn chắc chắn muốn xóa?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        referenceSV.whereEqualTo("emailSV",item.getEmailSV()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                    String id = documentSnapshot.getId();
                                    referenceSV.document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
        return listsv.size();
    }

    class SinhVienViewHolder extends RecyclerView.ViewHolder{
        TextView hosv,tensv,emailsv,masv,makhoasv,tenkhoa,malopsv;
        Button editsv, deletesv;
        public SinhVienViewHolder(@NonNull View view) {
            super(view);

            hosv= (TextView) view.findViewById(R.id.ttHoSv);
            tensv= (TextView) view.findViewById(R.id.ttTenSv);
            emailsv= (TextView) view.findViewById(R.id.ttEmailSv);
            masv= (TextView) view.findViewById(R.id.ttMaSv);
            makhoasv= (TextView) view.findViewById(R.id.ttKhoaSv);
            tenkhoa= (TextView) view.findViewById(R.id.ttTenKhoaSv);
            malopsv= (TextView) view.findViewById(R.id.ttMaLopSv);
            editsv=(Button) view.findViewById(R.id.btnEditSv);
            deletesv=(Button)  view.findViewById(R.id.btnDeleteSv);
        }
    }
}
