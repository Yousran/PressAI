package com.example.pressai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_profile extends AppCompatActivity {

    Button simpan_btn, link_kembali_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
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
