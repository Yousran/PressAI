package com.example.pressai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationBarView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.item_1) {
                    // Handle item 1 reselection
                    return true;
                } else if (id == R.id.item_2) {
                    // Handle item 2 reselection
                    return true;
                } else if (id == R.id.item_3) {
                    // Handle item 3 reselection
                    return true;
                } else if (id == R.id.item_4) {
                    // Handle item 4 reselection
                    return true;
                }
                return false;
            }
        });

    }
}