package com.example.pressai;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class TestFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerViewUjian;
    DatabaseReference databaseTest;
    RiwayatUjianAdapter riwayatUjianAdapter;
    ArrayList<DataUjian> dataUjian;
    String username;

    public TestFragment() {
        // Required empty public constructor
    }

    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
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
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPref.getString("username", "Mahasiswa");

        setupRecyclerViewUjian(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button join_btn = view.findViewById(R.id.join_btn);

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanTestFragment scanTestFragment = new ScanTestFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_test,scanTestFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Query queryujian = databaseTest.orderByChild("tanggal_test");
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
    private void setupRecyclerViewUjian(View view) {
        recyclerViewUjian = view.findViewById(R.id.recycler_ujian);
        databaseTest = FirebaseDatabase.getInstance().getReference("users/" + username + "/test/");

        recyclerViewUjian.setHasFixedSize(true);
        recyclerViewUjian.setLayoutManager(new LinearLayoutManager(getContext()));

        dataUjian = new ArrayList<>();
        riwayatUjianAdapter = new RiwayatUjianAdapter(getContext(),dataUjian);
        recyclerViewUjian.setAdapter(riwayatUjianAdapter);
    }

}