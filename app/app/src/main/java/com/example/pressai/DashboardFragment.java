package com.example.pressai;

import static com.example.pressai.R.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;
import com.google.android.material.navigation.NavigationBarView;


public class DashboardFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerViewKehadiran;
    private RecyclerView recyclerViewUjian;

    public DashboardFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TextView view_all_test = view.findViewById(R.id.view_all_ujian);
        TextView view_all_kehadiran = view.findViewById(R.id.view_all_kehadiran);
        TextView nama_mahasiswa = view.findViewById(R.id.nama_mahasiswa);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        nama_mahasiswa.setText(sharedPref.getString("username", "Mahasiswa"));

        view_all_kehadiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationBarView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
                bottomNavigationView.setSelectedItemId(R.id.item_3);

                HistoryPresensiFragment historyPresensiFragment = new HistoryPresensiFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, historyPresensiFragment);
                fragmentTransaction.commit();
            }
        });

        view_all_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationBarView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
                bottomNavigationView.setSelectedItemId(R.id.item_2);
            }
        });

        recyclerViewKehadiran = view.findViewById(R.id.recycler_kehadiran);
        recyclerViewUjian = view.findViewById(R.id.recycler_ujian);

        setupRecyclerViewKehadiran();
        setupRecyclerViewUjian();

        return view;
    }

    private void setupRecyclerViewKehadiran() {
        List<RiwayatItem> kehadiranList = new ArrayList<>();
        kehadiranList.add(new RiwayatItem("Pendidikan Agama Islam", "January 1, 2024", 95));
        kehadiranList.add(new RiwayatItem("Pendidikan Agama Islam", "January 1, 2024", 95));
        kehadiranList.add(new RiwayatItem("Pendidikan Agama Islam", "January 1, 2024", 95));
        kehadiranList.add(new RiwayatItem("Pendidikan Agama Islam", "January 1, 2024", 95));
        // Tambahkan data kehadiran lainnya ke dalam kehadiranList sesuai kebutuhan

        RiwayatAdapter kehadiranAdapter = new RiwayatAdapter(kehadiranList);
        recyclerViewKehadiran.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewKehadiran.setAdapter(kehadiranAdapter);
    }

    private void setupRecyclerViewUjian() {
        List<RiwayatItem> ujianList = new ArrayList<>();
        ujianList.add(new RiwayatItem("Pendidikan Agama Islam", "January 1, 2024", 95));
        ujianList.add(new RiwayatItem("Pendidikan Agama Islam", "January 1, 2024", 95));
        ujianList.add(new RiwayatItem("Pendidikan Agama Islam", "January 1, 2024", 95));
        ujianList.add(new RiwayatItem("Pendidikan Agama Islam", "January 1, 2024", 95));
        // Tambahkan data ujian ke dalam ujianList

        RiwayatAdapter ujianAdapter = new RiwayatAdapter(ujianList);
        recyclerViewUjian.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewUjian.setAdapter(ujianAdapter);
    }
}
