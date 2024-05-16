package com.example.pressai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class ViewScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);
        TextView scoreTextView = findViewById(R.id.score);
        Button kembali_btn = findViewById(R.id.kembali_btn);

        // Retrieve the average score from intent
        double averageScore = getIntent().getDoubleExtra("average_score", 0); // Default to 0 if not found
        scoreTextView.setText(String.format(Locale.getDefault(), "%.0f", averageScore));
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(this, R.raw.success);
        mediaPlayer.start();

        kembali_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent layout_dashboard = new Intent(getApplicationContext(),Dashboard.class);
                startActivity(layout_dashboard);
            }
        });
    }
}