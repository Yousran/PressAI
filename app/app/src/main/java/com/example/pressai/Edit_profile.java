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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_profile extends AppCompatActivity {

    TextInputLayout NIMInput, emailInput;
    TextView usernameTextview;
    Button simpanBtn, kembaliBtn;

    String username, nim, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Inisialisasi komponen UI
        usernameTextview = findViewById(R.id.edit_text_username);
        NIMInput = findViewById(R.id.edit_text_nim);
        emailInput = findViewById(R.id.edit_text_email);
        simpanBtn = findViewById(R.id.simpan_btn);
        kembaliBtn = findViewById(R.id.link_kembali_btn);

        // Mendapatkan data pengguna dari SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPref.getString("username", "Mahasiswa");
        nim = sharedPref.getString("NIM", null);
        email = sharedPref.getString("Email", null);

        // Menampilkan data pengguna
        usernameTextview.setText(username);
        NIMInput.getEditText().setText(nim);
        emailInput.getEditText().setText(email);

        // Mendengarkan klik tombol Simpan
        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

        // Mendengarkan klik tombol Kembali
        kembaliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Menutup aktivitas ini
            }
        });
    }

    // Metode untuk menyimpan data pengguna
    private void saveUserData() {
        // Mendapatkan nilai yang diubah oleh pengguna
        String newNIM = NIMInput.getEditText().getText().toString().trim();
        String newEmail = emailInput.getEditText().getText().toString().trim();

        // Memeriksa apakah email dan nim tidak kosong
        if (newNIM.isEmpty() || newEmail.isEmpty()) {
            // Menampilkan peringatan jika email atau nim kosong
            Toast.makeText(Edit_profile.this, "NIM dan Email harus diisi!", Toast.LENGTH_SHORT).show();
        } else {
            // Memeriksa apakah ada perubahan yang perlu disimpan
            if (!newNIM.equals(nim) || !newEmail.equals(email)) {
                // Mendapatkan referensi database pengguna
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(username);

                userRef.child("NIM").setValue(newNIM);
                userRef.child("Email").setValue(newEmail)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Menampilkan pesan sukses
                                Toast.makeText(Edit_profile.this, "Data pengguna berhasil diperbarui", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Menampilkan pesan gagal jika penyimpanan gagal
                                Toast.makeText(Edit_profile.this, "Gagal memperbarui data pengguna", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Simpan juga perubahan ke SharedPreferences
                SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("NIM", newNIM);
                editor.putString("Email", newEmail);
                editor.apply();
            } else {
                // Tidak ada perubahan yang perlu disimpan
                Toast.makeText(Edit_profile.this, "Tidak ada perubahan yang perlu disimpan", Toast.LENGTH_SHORT).show();
            }
        }
    }
}