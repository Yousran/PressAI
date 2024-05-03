package com.example.pressai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_profile extends AppCompatActivity {

    TextInputLayout usernameInput, NIMInput, emailInput;
    Button simpanBtn, kembaliBtn;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);

        // Mendapatkan informasi pengguna yang sedang login dari SharedPreferences
        String currentUserKey = sharedPreferences.getString("current_user_key", "");

        usernameInput = findViewById(R.id.edit_text_username);
        NIMInput = findViewById(R.id.edit_text_nim);
        emailInput = findViewById(R.id.edit_text_email);
        simpanBtn = findViewById(R.id.simpan_btn);
        kembaliBtn = findViewById(R.id.link_kembali_btn);

        // Menampilkan data profil pengguna yang sedang login
        showUserData(currentUserKey);

        // ...
    }

    private void showUserData(String currentUserKey) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserKey);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String username = dataSnapshot.hasChild("username") ? dataSnapshot.child("username").getValue().toString() : "";
                    String NIM = dataSnapshot.hasChild("NIM") ? dataSnapshot.child("NIM").getValue().toString() : "";
                    String email = dataSnapshot.hasChild("Email") ? dataSnapshot.child("Email").getValue().toString() : "";

                    // Menampilkan data pada TextInputLayout
                    usernameInput.getEditText().setText(username);
                    NIMInput.getEditText().setText(NIM);
                    emailInput.getEditText().setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Penanganan kesalahan jika ada
                Toast.makeText(Edit_profile.this, "Gagal mengambil data pengguna", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ...
}
