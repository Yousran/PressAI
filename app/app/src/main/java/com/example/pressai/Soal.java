package com.example.pressai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Soal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal);
        Button btn_sebelumnya = findViewById(R.id.btn_sebelumnya);
        btn_sebelumnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent layout_dashboard = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(layout_dashboard);
            }
        });
    }
}