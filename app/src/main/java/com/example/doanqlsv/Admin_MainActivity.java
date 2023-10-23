package com.example.doanqlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.doanqlsv.Fragment.ChangePasswordAd_Fragment;
import com.example.doanqlsv.Fragment.ChangePasswordSv_Fragment;
import com.example.doanqlsv.Fragment.Danh_Sach_Diem_Fragment;
import com.example.doanqlsv.Fragment.Fragment_ListGV;
import com.example.doanqlsv.Fragment.Fragment_ListSV;
import com.example.doanqlsv.Fragment.InforAdmin_Fragment;
import com.example.doanqlsv.Fragment.LichHoc_Fragment;
import com.example.doanqlsv.Fragment.LichThi_Fragment;
import com.example.doanqlsv.Model.GiangVien;
import com.google.android.material.navigation.NavigationView;

public class Admin_MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    ActionBar bar;
    String documentIDADMIN;
    GiangVien gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        toolbar = (Toolbar) findViewById(R.id.toolbarAdmin);
        setSupportActionBar(toolbar);
        loadFragment(new InforAdmin_Fragment());
        bar = getSupportActionBar();
        bar.setTitle("Thông tin tài khoản");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_admin);
        navigationView = (NavigationView) findViewById(R.id.nav_admin);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navi_open_string, R.string.navi_close_string);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getOnItemSelectedListener();
        Bundle goi = getIntent().getExtras();
    }

    private void getOnItemSelectedListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Hình như không có lịch thi và thời khóa biểu

                if (item.getItemId() == R.id.item_Gv) {
                    bar.setTitle("Danh sách giáo viên");
                    loadFragment(new Fragment_ListGV());

                } else if (item.getItemId() == R.id.item_SV) {
                    bar.setTitle("Danh sách sinh viên");
                    loadFragment(new Fragment_ListSV());

                } else if (item.getItemId() == R.id.item_doiMKAdmin) {
                    bar.setTitle("Đổi mật khẩu");
                    loadFragment(new ChangePasswordAd_Fragment());

                } else if (item.getItemId() == R.id.item_lichthi) {
                    bar.setTitle("Lịch thi");
                    loadFragment(new LichThi_Fragment()) ;

                } else if (item.getItemId() == R.id.item_bangDiem) {
                    bar.setTitle("Bảng điểm");
                    loadFragment(new Danh_Sach_Diem_Fragment());

                } else if (item.getItemId() == R.id.item_dangXuatAdmin) {
                    Intent i = new Intent(Admin_MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                else if (item.getItemId() == R.id.item_infoAdmnin) {
                    bar.setTitle("Thông tin admin");
                    loadFragment(new InforAdmin_Fragment());
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    public GiangVien getGv() {
        return gv;
    }

    public void setGv(GiangVien gv) {
        this.gv = gv;
    }

    public String getDocumentIDADMIN() {
        return documentIDADMIN;
    }

    public void setDocumentIDADMIN(String documentIDADMIN) {
        this.documentIDADMIN = documentIDADMIN;
    }

    void loadFragment(Fragment fg)
    {
        String doc_id_sv=getIntent().getStringExtra("documentID_ADMIN");
        documentIDADMIN=doc_id_sv;
        //
        gv = getIntent().getParcelableExtra("admin");
        GiangVien g = gv;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_ad,fg);//frgament hien toi fragment moi

        fragmentTransaction.commit();
    }
}