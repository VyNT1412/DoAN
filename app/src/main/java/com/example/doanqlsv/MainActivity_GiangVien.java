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

import com.example.doanqlsv.Fragment.ChangePasswordGv_Fragment;
import com.example.doanqlsv.Fragment.DiemMonHoc_Fragment;
import com.example.doanqlsv.Fragment.Fragment_ListSV;
import com.example.doanqlsv.Fragment.InforGiangVien_Fragment;
import com.example.doanqlsv.Fragment.LichDay_Fragment;
import com.example.doanqlsv.Model.GiangVien;
import com.google.android.material.navigation.NavigationView;

public class MainActivity_GiangVien extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    ActionBar bar;
    String documentIDGV;
    GiangVien gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_giang_vien);
        toolbar = (Toolbar) findViewById(R.id.toolbarGV);
        setSupportActionBar(toolbar);
        loadFragment(new InforGiangVien_Fragment());
        bar = getSupportActionBar();
        bar.setTitle("Thông tin tài khoản");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_giangvien);
        navigationView = (NavigationView) findViewById(R.id.nav_giangvien);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navi_open_string, R.string.navi_close_string);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getOnItemSelectedListener();
        Bundle goi = getIntent().getExtras();
    }

    private void getOnItemSelectedListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem item){
            //set tittle

            if (item.getItemId() == R.id.item_listSVgv) {
                bar.setTitle("Danh sách sinh viên");
                loadFragment(new Fragment_ListSV());

            } else if (item.getItemId() == R.id.item_lichdaygv) {
                bar.setTitle("Thời khóa biểu dạy học");
                loadFragment(new LichDay_Fragment());
            } else if (item.getItemId() == R.id.item_bangDiemgv) {
                bar.setTitle("Bảng điểm");
                loadFragment(new DiemMonHoc_Fragment());

            } else if (item.getItemId() == R.id.item_doiMKGV) {
                bar.setTitle("Đổi mật khẩu");
                loadFragment(new ChangePasswordGv_Fragment());

            } else if (item.getItemId() == R.id.item_infoGV) {
                bar.setTitle("Thông tin tài khoản");
                loadFragment(new InforGiangVien_Fragment());

            } else if (item.getItemId() == R.id.item_dangXuatGV) {
                Intent i = new Intent(MainActivity_GiangVien.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        });
    }

    public String getDocumentIDGV() {
        return documentIDGV;
    }

    public void setDocumentIDGV(String documentIDGV) {
        this.documentIDGV = documentIDGV;
    }

    public GiangVien getGv() {
        return gv;
    }

    public void setGv(GiangVien gv) {
        this.gv = gv;
    }
    void loadFragment(Fragment fg)
    {
        String doc_id_sv=getIntent().getStringExtra("documentID_GV");
        documentIDGV=doc_id_sv;
        //
        gv = getIntent().getParcelableExtra("giangvien");
        GiangVien g = gv;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_gv,fg);//frgament hien toi fragment moi

        fragmentTransaction.commit();
    }
}