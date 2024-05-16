package com.example.pressai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Soal extends AppCompatActivity {

    double averageScore = 0;
    private int exitCount = 0;

    private class ChatGPTTask extends AsyncTask<String, Void, String> {
        private DataJawaban currentJawaban;

        public ChatGPTTask(DataJawaban jawaban) {
            this.currentJawaban = jawaban;
        }

        @Override
        protected String doInBackground(String... params) {
            String jawaban = params[0];
            String soal = params[1];
            String kunci_jawaban = params[2];
            try {
                return ChatGPTAPI.chatGPT(jawaban, soal, kunci_jawaban);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && !result.isEmpty()) {
                try {
                    int score = Integer.parseInt(result);
                    currentJawaban.skor = score;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(Soal.this, "Score for : " + currentJawaban.getSkor(), Toast.LENGTH_LONG).show();

            evaluatedAnswers++;
            if (evaluatedAnswers == dataJawabans.size()) {
                Toast.makeText(Soal.this, "All answers evaluated", Toast.LENGTH_SHORT).show();
                updateFirebaseWithResults();

            }
        }
    }

    private int currentIndex = 0;
    private ArrayList<DataJawaban> dataJawabans;
    private TextView soal_test;
    private EditText editText_jawaban;
    private int evaluatedAnswers = 0;
    private CountDownTimer countDownTimer;
    private TextView countdownMinute;
    private TextView countdownSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal);
        Button btn_sebelumnya = findViewById(R.id.btn_sebelumnya);
        Button btn_selanjutnya = findViewById(R.id.btn_selanjutnya);
        TextInputLayout textInputLayout = findViewById(R.id.jawaban);
        editText_jawaban = textInputLayout.getEditText();
        soal_test = findViewById(R.id.soal_test);
        countdownMinute = findViewById(R.id.countdown_minute);
        countdownSecond = findViewById(R.id.countdown_second);

        long duration = getIntent().getLongExtra("duration", 0); // Menerima durasi
        startTimer(duration); // Memulai timer

        dataJawabans = (ArrayList<DataJawaban>) getIntent().getSerializableExtra("dataJawabans");
        displaySoal();

        btn_sebelumnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex > 0) {
                    saveJawaban();
                    currentIndex--;
                    displaySoal();
                    btn_selanjutnya.setText("Selanjutnya");
                }
            }
        });

        btn_selanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveJawaban();
                if (currentIndex < dataJawabans.size() - 1) {
                    currentIndex++;
                    displaySoal();
                    if (currentIndex == dataJawabans.size() - 1){
                        btn_selanjutnya.setText("Selesai");
                    }
                } else {
                    evaluateAllAnswers();
                }
            }
        });
    }
    private void startTimer(long durationMinutes) {
        long durationMillis = durationMinutes * 60 * 1000;

        countDownTimer = new CountDownTimer(durationMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                long minutesLeft = millisUntilFinished / 1000 / 60;
                long secondsLeft = (millisUntilFinished / 1000) % 60;
                countdownMinute.setText(String.format(Locale.getDefault(), "%02d", minutesLeft));
                countdownSecond.setText(String.format(Locale.getDefault(), "%02d", secondsLeft));
            }

            public void onFinish() {
                countdownMinute.setText("00");
                countdownSecond.setText("00");
                evaluateAllAnswers();
            }
        }.start();
    }

    private void displaySoal() {
        DataJawaban currentSoal = dataJawabans.get(currentIndex);
        soal_test.setText(currentSoal.getPertanyaan());
        editText_jawaban.setText(currentSoal.getJawaban());
    }

    private void saveJawaban() {
        String jawaban = editText_jawaban.getText().toString();
        dataJawabans.get(currentIndex).setJawaban(jawaban);
    }

    private void evaluateAllAnswers() {
        for (DataJawaban jawaban : dataJawabans) {
            new ChatGPTTask(jawaban).execute(jawaban.getJawaban(), jawaban.getPertanyaan(), jawaban.getKunci_jawaban());
        }
    }
    private void updateFirebaseWithResults() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

        int totalScore = 0;
        for (DataJawaban jawaban : dataJawabans) {
            totalScore += jawaban.getSkor();
        }
        averageScore = ((double) totalScore / (dataJawabans.size() * 5)) * 100; // Calculate the scaled average score
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        SimpleDateFormat cardinaltf = new SimpleDateFormat("mm:HH:dd:MM:yyyy", Locale.getDefault());
        String currentTimestamp = sdf.format(new Date()); // Get the current timestamp
        String cardinaldat = cardinaltf.format(new Date());

        DatabaseReference testRef = databaseRef.child("test/" + dataJawabans.get(0).getTest_code());
        testRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mata_kuliah_name = snapshot.child("mata_kuliah_name").getValue(String.class);
                String tanggal_test = snapshot.child("tanggal_test").getValue(String.class);

                Map<String, Object> updates = new HashMap<>();
                for (DataJawaban jawaban : dataJawabans) {
                    String userBasePath = "users/" + jawaban.getUsername() + "/test/" + jawaban.getTest_code();
                    String answerPath = userBasePath + "/jawaban/" + jawaban.getSoal_code();
                    Map<String, Object> jawabanDetails = new HashMap<>();
                    jawabanDetails.put("pertanyaan", jawaban.getPertanyaan());
                    jawabanDetails.put("jawaban", jawaban.getJawaban());
                    jawabanDetails.put("soal_code", jawaban.getSoal_code());
                    jawabanDetails.put("test_code", jawaban.getTest_code());
                    jawabanDetails.put("skor", jawaban.getSkor());

                    // Update user-specific test details
                    updates.put(answerPath, jawabanDetails);

                    // Update common test summary details for the user
                    updates.put(userBasePath + "/test_score", averageScore);
                    updates.put(userBasePath + "/mata_kuliah_name", mata_kuliah_name);
                    updates.put(userBasePath + "/tanggal_test", tanggal_test);
                    updates.put(userBasePath + "/created_at", cardinaldat);

                    // Add data under the test_code for each user
                    String testUserPath = "test/" + jawaban.getTest_code() + "/users/" + jawaban.getUsername();
                    updates.put(testUserPath + "/test_score", averageScore);
                    updates.put(testUserPath + "/mata_kuliah_name", mata_kuliah_name);
                    updates.put(testUserPath + "/tanggal_test", tanggal_test);
                    updates.put(testUserPath + "/created_at", cardinaldat);
                    String testAnswerpath = testUserPath + "/jawaban/" + jawaban.getSoal_code();
                    updates.put(testUserPath + "/keluar_aplikasi", exitCount);
                    updates.put(testAnswerpath, jawabanDetails);
                }

                databaseRef.updateChildren(updates, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Log.d("Firebase", "All data updated successfully!");
                            Intent layout_view_score = new Intent(getApplicationContext(), ViewScoreActivity.class);
                            layout_view_score.putExtra("average_score", averageScore); // Passing the average score
                            startActivity(layout_view_score);
                        } else {
                            Log.d("Firebase", "Failed to update data: " + databaseError.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to fetch test details: " + error.getMessage());
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        exitCount++;
        Log.d("SoalActivity", "Pengguna keluar dari aplikasi, total keluar: " + exitCount);
    }
}
