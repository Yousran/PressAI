package com.example.pressai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TestLobbyFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TestLobbyFragment() {
        // Required empty public constructor
    }

    public static TestLobbyFragment newInstance(String param1, String param2) {
        TestLobbyFragment fragment = new TestLobbyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_lobby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView nama_matakuliah = view.findViewById(R.id.nama_matakuliah);
        TextView nama_test = view.findViewById(R.id.nama_test);
        TextView tanggal_test = view.findViewById(R.id.tanggal_test);
        TextView waktu_test = view.findViewById(R.id.waktu_test);
        TextView durasi_test = view.findViewById(R.id.durasi_test);
        Button mulai_btn = view.findViewById(R.id.mulai_btn);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        String username = sharedPref.getString("username", "Mahasiswa");

        Bundle arguments = getArguments();
        if (arguments != null) {
            nama_matakuliah.setText(arguments.getString("mata_kuliah_name"));
            nama_test.setText(arguments.getString("test_name"));
            tanggal_test.setText("Tanggal : "+arguments.getString("tanggal_test"));
            waktu_test.setText("Terbuka : "+arguments.getString("awal_waktu") + " - " + arguments.getString("akhir_waktu"));
            durasi_test.setText("Durasi : "+arguments.getString("durasi_test") + " Menit");
        }

        mulai_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database_test = FirebaseDatabase.getInstance().getReference("test/"+arguments.getString("test_code")+"/soal");
                database_test.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<DataJawaban> dataJawabanList = new ArrayList<>();
                        long duration = 0;
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            DataJawaban dataJawaban = new DataJawaban();
                            dataJawaban.pertanyaan = childSnapshot.child("pertanyaan").getValue(String.class);
                            dataJawaban.kunci_jawaban = childSnapshot.child("kunci_jawaban").getValue(String.class);
                            dataJawaban.soal_code = childSnapshot.child("soal_code").getValue(String.class);
                            dataJawaban.test_code = childSnapshot.child("test_code").getValue(String.class);
                            dataJawaban.username = username;
                            dataJawabanList.add(dataJawaban);
                        }
                        Intent layout_soal = new Intent(getContext(), Soal.class);
                        duration = Long.parseLong(arguments.getString("durasi_test"));
                        layout_soal.putParcelableArrayListExtra("dataJawabans", dataJawabanList);
                        layout_soal.putExtra("duration", duration);
                        startActivity(layout_soal);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle possible errors
                    }
                });
            }
        });
    }
}
