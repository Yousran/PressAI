package com.example.pressai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    Button signup_btn;
    TextInputEditText username,nim,email,password,confirm_password;

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = findViewById(R.id.edit_text_username);
        nim = findViewById(R.id.edit_text_nim);
        email = findViewById(R.id.edit_text_email);
        password = findViewById(R.id.edit_text_password);
        confirm_password = findViewById(R.id.edit_text_confirm_password);



        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pressai-kelompok5-default-rtdb.asia-southeast1.firebasedatabase.app/");
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String NIM = nim.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String Konfirmasi_password = confirm_password.getText().toString();

                if (user.isEmpty() || NIM.isEmpty() || Email.isEmpty() || Password.isEmpty() || Konfirmasi_password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ada Data Yang Masih Kosong!!", Toast.LENGTH_SHORT).show();
                }else {
                    database = FirebaseDatabase.getInstance().getReference("users");
                    database.child(user).child("username").setValue(user);
                    database.child(user).child("NIM").setValue(NIM);
                    database.child(user).child("Email").setValue(Email);
                    database.child(user).child("Password").setValue(Password);
                    database.child(user).child("Konfirmasi Password").setValue(Konfirmasi_password);

                    Toast.makeText(getApplicationContext(), "Data Berhasil Tersimpan", Toast.LENGTH_SHORT).show();
                    Intent layout_login = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(layout_login);
                }
            }
        });

    }
}