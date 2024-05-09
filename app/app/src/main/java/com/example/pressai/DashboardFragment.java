package com.example.pressai;

import static com.example.pressai.R.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Collections;
import java.util.List;

import android.widget.TextView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class DashboardFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String username;
    private RecyclerView recyclerViewKehadiran;
    private RecyclerView recyclerViewUjian;
    DatabaseReference databaseKehadiran;
    DatabaseReference databaseTest;
    KehadiranAdapter kehadiranAdapter;
    ArrayList<DataKehadiran> dataKehadiran;

    RiwayatUjianAdapter riwayatUjianAdapter;
    ArrayList<DataUjian> dataUjian;

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
        username = sharedPref.getString("username", "Mahasiswa");
        nama_mahasiswa.setText(username);

        setupRecyclerViewKehadiran(view);
        setupRecyclerViewUjian(view);



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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Query query = databaseKehadiran.orderByChild("created_at").limitToLast(5);
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataKehadiran.clear();
                ArrayList<DataSnapshot> dataList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dataList.add(dataSnapshot);
                }

                Collections.reverse(dataList);
                for (DataSnapshot dataSnapshot : dataList){
                    DataKehadiran data = dataSnapshot.getValue(DataKehadiran.class);
                    dataKehadiran.add(data);
                }
                kehadiranAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Query queryujian = databaseTest.orderByChild("tanggal_test").limitToLast(5);
        queryujian.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataUjian.clear();
                ArrayList<DataSnapshot> dataList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dataList.add(dataSnapshot);
                }

                Collections.reverse(dataList);
                for (DataSnapshot dataSnapshot : dataList){
                    DataUjian data = dataSnapshot.getValue(DataUjian.class);
                    dataUjian.add(data);
                }
                riwayatUjianAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setupRecyclerViewKehadiran(View view) {
        recyclerViewKehadiran = view.findViewById(R.id.recycler_kehadiran);
        databaseKehadiran = FirebaseDatabase.getInstance().getReference("users/"+username+"/kehadiran/");

        recyclerViewKehadiran.setHasFixedSize(true);
        recyclerViewKehadiran.setLayoutManager(new LinearLayoutManager(getContext()));

        dataKehadiran = new ArrayList<>();
        kehadiranAdapter = new KehadiranAdapter(getContext(),dataKehadiran);
        recyclerViewKehadiran.setAdapter(kehadiranAdapter);
    }

    private void setupRecyclerViewUjian(View view) {
        recyclerViewUjian = view.findViewById(id.recycler_ujian);
        databaseTest = FirebaseDatabase.getInstance().getReference("users/" + username + "/test/");

        recyclerViewUjian.setHasFixedSize(true);
        recyclerViewUjian.setLayoutManager(new LinearLayoutManager(getContext()));

        dataUjian = new ArrayList<>();
        riwayatUjianAdapter = new RiwayatUjianAdapter(getContext(),dataUjian);
        recyclerViewUjian.setAdapter(riwayatUjianAdapter);
    }
}
