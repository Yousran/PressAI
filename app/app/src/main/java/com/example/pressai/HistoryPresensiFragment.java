package com.example.pressai;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.prefs.PreferenceChangeListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryPresensiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryPresensiFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    KehadiranAdapter kehadiranAdapter;
    ArrayList<DataKehadiran> dataKehadiran;

    public HistoryPresensiFragment() {
        // Required empty public constructor
    }

    public static HistoryPresensiFragment newInstance(String param1, String param2) {
        HistoryPresensiFragment fragment = new HistoryPresensiFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_presensi, container, false);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "Mahasiswa");

        recyclerView = view.findViewById(R.id.recyclerViewKehadiran);
        databaseReference = FirebaseDatabase.getInstance().getReference("users/"+username+"/kehadiran/");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataKehadiran = new ArrayList<>();
        kehadiranAdapter = new KehadiranAdapter(getContext(),dataKehadiran);
        recyclerView.setAdapter(kehadiranAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Query query = databaseReference.orderByChild("tanggal_sesi");
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
    }
}