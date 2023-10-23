package com.example.doanqlsv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.doanqlsv.Fragment.ChangePasswordSv_Fragment;
import com.example.doanqlsv.Fragment.DiemMonHoc_Fragment;
import com.example.doanqlsv.Fragment.InforSinhVien_Fragment;
import com.example.doanqlsv.Fragment.LichHoc_Fragment;
import com.example.doanqlsv.Fragment.LichThi_Fragment;
import com.example.doanqlsv.Fragment.XemDiem_SV_Fragment;
import com.example.doanqlsv.Model.SinhVien;
import com.google.android.material.navigation.NavigationView;


public class MainActivity_SinhVien extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    ActionBar bar;
    String documentIDSV ;
    public String getPassword() {
        return password;
    }

    public String getHosv() {
        return hosv;
    }

    public String getTensv() {
        return tensv;
    }

    public String getQuequan() {
        return quequan;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public String getEmailsv() {
        return emailsv;
    }

    public String getMalop() {
        return malop;
    }

    public String getMssv() {
        return mssv;
    }
SinhVien s;
    String password ;
    String hosv ;
    String tensv ;
    String quequan ;
    String gioitinh ;
    String ngaysinh;
    String emailsv ;
    String malop,mssv ;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sinh_vien);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadFragment(new InforSinhVien_Fragment());
        bar = getSupportActionBar();
        bar.setTitle("Thông tin tài khoản");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_sinhvien);
        navigationView = (NavigationView) findViewById(R.id.nav_sinhvien);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navi_open_string,R.string.navi_close_string);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getOnItemSelectedListener();
        Log.d("","++++***"+s.getMaSV());
        //hien hien du lieu tu bundle
        Bundle goi = getIntent().getExtras();

    }
    //

    private void getOnItemSelectedListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //set tittle

                if(item.getItemId()==R.id.item_diem)
                {
                    bar.setTitle("Bảng điểm");
                    loadFragment(new XemDiem_SV_Fragment());

                } else if (item.getItemId()==R.id.item_lichthi) {
                    bar.setTitle("Lịch thi");
                    loadFragment(new LichThi_Fragment());
                } else if (item.getItemId()==R.id.item_lichhoc) {
                    bar.setTitle("Thời khóa biểu");
                    loadFragment(new LichHoc_Fragment());

                }else if (item.getItemId()==R.id.item_doiMKSV) {
                    bar.setTitle("Đổi mật khẩu");
                    loadFragment(new ChangePasswordSv_Fragment());

                }
                else if (item.getItemId()==R.id.item_infoSv) {
                    bar.setTitle("Thông tin tài khoản");
                    loadFragment(new InforSinhVien_Fragment());

                }
                else if (item.getItemId()==R.id.item_dangXuatSv) {
                    Intent i =new Intent(MainActivity_SinhVien.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public String getDocumentIDSV() {
        return documentIDSV;
    }

    public void setDocumentIDSV(String documentIDSV) {
        this.documentIDSV = documentIDSV;
    }

    public SinhVien getS() {
        return s;
    }

    public void setS(SinhVien s) {
        this.s = s;
    }

    void loadFragment(Fragment fg)
    {
       String doc_id_sv=getIntent().getStringExtra("mssv");
       documentIDSV=doc_id_sv;
       //
        s = getIntent().getParcelableExtra("sinhvien");
        SinhVien sv = s;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment,fg);//frgament hien toi fragment moi

        fragmentTransaction.commit();
    }


}