package com.example.pressai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class Dashboard extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        replaceFragment(new DashboardFragment());

        NavigationBarView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.item_1) {
                    replaceFragment(new DashboardFragment());
                    return true;
                } else if (id == R.id.item_2) {
                    replaceFragment(new TestFragment());
                    return true;
                } else if (id == R.id.item_3) {
                    replaceFragment(new PresensiFragment());
                    return true;
                } else if (id == R.id.item_4) {
                    replaceFragment(new ProfilFragment());
                    return true;
                }
                return false;
            }
        });


    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }
}
