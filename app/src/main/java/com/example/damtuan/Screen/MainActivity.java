package com.example.damtuan.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.damtuan.DAO.ThuThuDAO;
import com.example.damtuan.R;
import com.example.damtuan.fragment.AddUserFragment;
import com.example.damtuan.fragment.ChangePassFragment;
import com.example.damtuan.fragment.DoanhThuFragment;
import com.example.damtuan.fragment.LoaiSachFragment;
import com.example.damtuan.fragment.PhieuMuonFragment;
import com.example.damtuan.fragment.SachFragment;
import com.example.damtuan.fragment.ThanhVienFragment;
import com.example.damtuan.fragment.TopFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    View mHeaderView;
    TextView edUser;
    ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.baseline_menu_24);
        ab.setDisplayHomeAsUpEnabled(true);
        //dung fragment PhieuMuon làm home
        FragmentManager manager = getSupportFragmentManager();
        PhieuMuonFragment phieuMuonFragment = new PhieuMuonFragment();
        manager.beginTransaction()
                .replace(R.id.fLContent, phieuMuonFragment)
                .commit();
        NavigationView nv = findViewById(R.id.nvView);
        mHeaderView = nv.getHeaderView(0);
        edUser = mHeaderView.findViewById(R.id.tvUser);
        Intent i = getIntent();
        String user = i.getStringExtra("user");
        edUser.setText(("welcome" + user));
        if (user.equalsIgnoreCase("admin")) {
            nv.getMenu().findItem(R.id.sub_AddUser).setVisible(true);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.nav_PhieuMuon) {
                    fragment = new PhieuMuonFragment();
                } else if (item.getItemId() == R.id.nav_LoaiSach) {
                    fragment = new LoaiSachFragment();
                } else if (item.getItemId() == R.id.nav_Sach) {
                    fragment = new SachFragment();
                } else if (item.getItemId() == R.id.nav_ThanhVien) {
                    fragment = new ThanhVienFragment();
                } else if (item.getItemId() == R.id.sub_Top) {
                    fragment = new TopFragment();
                } else if (item.getItemId() == R.id.sub_DoanhThu) {
                    fragment = new DoanhThuFragment();
                } else if (item.getItemId() == R.id.sub_AddUser) {
                    fragment = new AddUserFragment();
                } else if (item.getItemId() == R.id.sub_Pass) {
                    fragment = new ChangePassFragment();
                } else if (item.getItemId() == R.id.sub_Logout) {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fLContent, fragment).commit();
                    toolbar.setTitle(item.getTitle());
                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });
    }
}