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

    TextInputLayout usernameInput, NIMInput, emailInput;
    Button simpanBtn, kembaliBtn;

    String username, nim, Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Mengambil informasi pengguna dari SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPref.getString("username", null);
        nim = sharedPref.getString("nim", null);
        Email = sharedPref.getString("Email", null); // Menambahkan email di sini

        // Inisialisasi TextInputLayout dan Button
        usernameInput = findViewById(R.id.edit_text_username);
        NIMInput = findViewById(R.id.edit_text_nim);
        emailInput = findViewById(R.id.edit_text_email);
        simpanBtn = findViewById(R.id.simpan_btn);
        kembaliBtn = findViewById(R.id.link_kembali_btn);

        // Menampilkan data pengguna di TextInputLayout
        usernameInput.getEditText().setText(username);
        NIMInput.getEditText().setText(nim);
        emailInput.getEditText().setText(Email); // Menampilkan email di sini

        // Tambahkan logika untuk menyimpan data pengguna saat tombol Simpan ditekan
        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

        // Tambahkan aksi klik pada tombol Kembali
        kembaliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kembali ke halaman sebelumnya
                finish();
            }
        });
    }

    private void saveUserData() {
        // Mendapatkan nilai yang diubah oleh pengguna
        String newUsername = usernameInput.getEditText().getText().toString().trim();
        String newNIM = NIMInput.getEditText().getText().toString().trim();
        String newEmail = emailInput.getEditText().getText().toString().trim();

        // Logika untuk menyimpan data pengguna ke Firebase hanya jika ada perubahan
        if (!newUsername.equals(username) || !newNIM.equals(nim) || !newEmail.equals(Email)) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");

            // Jika pengguna mengubah nama pengguna
            if (!newUsername.equals(username)) {
                // Hapus entri lama
                userRef.child(username).removeValue();
            }

            // Menyimpan data ke Firebase
            userRef.child(newUsername).child("username").setValue(newUsername);
            userRef.child(newUsername).child("nim").setValue(newNIM);
            userRef.child(newUsername).child("email").setValue(newEmail)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Menampilkan pesan sukses
                            Toast.makeText(Edit_profile.this, "Data pengguna berhasil diperbarui di Firebase", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Menampilkan pesan gagal jika penyimpanan gagal
                            Toast.makeText(Edit_profile.this, "Gagal memperbarui data pengguna di Firebase", Toast.LENGTH_SHORT).show();
                        }
                    });

            // Simpan juga perubahan ke SharedPreferences
            SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("username", newUsername);
            editor.putString("nim", newNIM);
            editor.putString("Email", newEmail);
            editor.apply();
        } else {
            // Tidak ada perubahan yang perlu disimpan
            Toast.makeText(Edit_profile.this, "Tidak ada perubahan yang perlu disimpan", Toast.LENGTH_SHORT).show();
        }
    }

}
