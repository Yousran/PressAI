package com.example.pressai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pressai.databinding.ActivityGantiPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class GantiPassword extends AppCompatActivity {

    ActivityGantiPasswordBinding binding;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGantiPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button simpan_btn = findViewById(R.id.simpan_btn);
        Button link_kembali_btn = findViewById(R.id.link_kembali_btn);

        simpan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.textView5.getEditText().getText().toString();
                String NIM = binding.textView6.getEditText().getText().toString();
                String Password = binding.textView7.getEditText().getText().toString();
                String konfirmasiPassword = binding.textView8.getEditText().getText().toString();

                updateData(username, NIM, Password, konfirmasiPassword);
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

    private void updateData(String username, String NIM, String Password, String konfirmasiPassword) {

        if (!Password.equals(konfirmasiPassword)) {
            Toast.makeText(this, "Password baru dan konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> user = new HashMap<>();
        user.put("NIM", NIM);
        user.put("Password", Password);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(username).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    binding.textView5.getEditText().setText("");
                    binding.textView6.getEditText().setText("");
                    binding.textView7.getEditText().setText("");
                    binding.textView8.getEditText().setText("");
                    Toast.makeText(GantiPassword.this, "Berhasil Disimpan", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(GantiPassword.this, "Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
