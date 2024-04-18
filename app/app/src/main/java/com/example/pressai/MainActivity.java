package com.example.pressai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button login_btn, link_signup_btn;
    TextInputEditText username, password;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_btn = findViewById(R.id.login_btn);
        link_signup_btn = findViewById(R.id.link_signup_btn);
        username = findViewById(R.id.edit_text_username);
        password = findViewById(R.id.edit_text_password);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim().toLowerCase();
                String Password = password.getText().toString();

                database = FirebaseDatabase.getInstance().getReference("users");

                if (user.isEmpty() || Password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username dan Password Salah!!", Toast.LENGTH_SHORT).show();
                } else {
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(user).exists()) {
                                String savedPassword = snapshot.child(user).child("Password").getValue(String.class);
                                if (savedPassword != null && savedPassword.equals(Password)) {
                                    Toast.makeText(getApplicationContext(), "Login Anda Berhasil!!", Toast.LENGTH_SHORT).show();
                                    Intent layout_dashboard = new Intent(getApplicationContext(), Dashboard.class);
                                    startActivity(layout_dashboard);
                                    finish(); // Optional: Tutup activity saat ini agar tidak bisa kembali ke halaman login
                                } else {
                                    Toast.makeText(getApplicationContext(), "Password Anda Salah!!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Username Anda belum Terdaftar!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        link_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent layout_signup = new Intent(getApplicationContext(), Signup.class);
                startActivity(layout_signup);
            }
        });
    }
}
