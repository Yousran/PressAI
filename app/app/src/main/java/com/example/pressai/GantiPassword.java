package com.example.pressai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

public class GantiPassword extends AppCompatActivity {

    Button simpan_btn,link_kembali_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_password);
        simpan_btn = findViewById(R.id.simpan_btn);
        link_kembali_btn = findViewById(R.id.link_kembali_btn);
        simpan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent layout_dashboard = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(layout_dashboard);
            }
        });
        link_kembali_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kembali ke halaman sebelumnya
                finish();
            }
        });
    }
}
