package com.example.pressai;

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

public class Signup extends AppCompatActivity {

    Button signup_btn;
    TextInputEditText edit_text_username,edit_text_nim,edit_text_email,edit_text_password,edit_text_confirm_password;

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup_btn = findViewById(R.id.signup_btn);
        edit_text_username = findViewById(R.id.edit_text_username);
        edit_text_nim = findViewById(R.id.edit_text_nim);
        edit_text_email = findViewById(R.id.edit_text_email);
        edit_text_password = findViewById(R.id.edit_text_password);
        edit_text_confirm_password = findViewById(R.id.edit_text_confirm_password);



        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pressai-7a882-default-rtdb.asia-southeast1.firebasedatabase.app/");
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = edit_text_username.getText().toString();
                String NIM = edit_text_nim.getText().toString();
                String Email = edit_text_email.getText().toString();
                String Password = edit_text_password.getText().toString();
                String Konfirmasi_password = edit_text_confirm_password.getText().toString();

                if (username.isEmpty() || NIM.isEmpty() || Email.isEmpty() || Password.isEmpty() || Konfirmasi_password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ada Data Yang Masih Kosong!!", Toast.LENGTH_SHORT).show();
                }else {
                    database = FirebaseDatabase.getInstance().getReference("users");
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(username).exists()) {
                                // user atau NIM sudah ada
                                Toast.makeText(getApplicationContext(), "Username atau NIM sudah ada!!", Toast.LENGTH_SHORT).show();
                            } else {
                                // user dan NIM belum ada, simpan data
                                database.child(username).child("username").setValue(username);
                                database.child(username).child("NIM").setValue(NIM);
                                database.child(username).child("Email").setValue(Email);
                                database.child(username).child("Password").setValue(Password);
                                database.child(username).child("Konfirmasi Password").setValue(Konfirmasi_password);

                                Toast.makeText(getApplicationContext(), "Data Berhasil Tersimpan", Toast.LENGTH_SHORT).show();
                                Intent layout_login = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(layout_login);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Koneksi ke database terputus!!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }
}