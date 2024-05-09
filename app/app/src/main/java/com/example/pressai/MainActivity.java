package com.example.pressai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button login_btn, link_signup_btn;
    TextInputEditText edit_text_username,edit_text_password;
    TextInputLayout input_username,input_password;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", null);
        super.onCreate(savedInstanceState);

        if (username!=null) {
            Intent dashboardIntent = new Intent(MainActivity.this, Dashboard.class);
            startActivity(dashboardIntent);
            finish();
        }else{
            setContentView(R.layout.activity_main);

            login_btn = findViewById(R.id.login_btn);
            link_signup_btn = findViewById(R.id.link_signup_btn);
            input_username = findViewById(R.id.input_username);
            input_password = findViewById(R.id.input_password);
            edit_text_username = findViewById(R.id.edit_text_username);
            edit_text_password = findViewById(R.id.edit_text_password);

            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = edit_text_username.getText().toString();
                    String password = edit_text_password.getText().toString();
                    database = FirebaseDatabase.getInstance().getReference("users");

                    if (username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Ada Data Yang Masih Kosong!!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Tampilkan ProgressBar
                        ProgressBar progressBar = findViewById(R.id.progressBar);
                        progressBar.setVisibility(View.VISIBLE);

                        database.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.child(username).exists()) {
                                    String nim = snapshot.child(username).child("NIM").getValue(String.class);
                                    if (snapshot.child(username).child("Password").getValue(String.class).equals(password)) {
                                        Intent loginIntent = new Intent(MainActivity.this, Dashboard.class);

                                        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("username", username);
                                        editor.putString("nim", nim);
                                        editor.apply();
                                        // Sembunyikan ProgressBar
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(loginIntent);
                                        finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password atau username salah", Toast.LENGTH_SHORT).show();
                                        input_username.setError("");
                                        input_password.setError("");
                                    }
                                } else {
                                    input_username.setError("");
                                    input_password.setError("");
                                    Toast.makeText(getApplicationContext(), "Password atau username salah", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            });

            link_signup_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent untuk menuju activity signup
                    Intent signupIntent = new Intent(MainActivity.this, Signup.class);
                    startActivity(signupIntent);
                }
            });
        }
    }
}
