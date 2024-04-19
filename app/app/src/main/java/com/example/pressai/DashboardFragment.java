package com.example.pressai;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

public class DashboardFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TextView view_all_test = view.findViewById(R.id.view_all_test);
        TextView view_all_kehadiran = view.findViewById(R.id.view_all_kehadiran);
        TextView nama_mahasiswa = view.findViewById(R.id.nama_mahasiswa);
        showUserData(nama_mahasiswa);

        view_all_kehadiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationBarView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
                bottomNavigationView.setSelectedItemId(R.id.item_3);

                HistoryPresensiFragment historyPresensiFragment = new HistoryPresensiFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,historyPresensiFragment);
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

    public void showUserData(TextView nama_mahasiswa){
        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra("username");
        String nim = intent.getStringExtra("nim");

        nama_mahasiswa.setText(username);
    }

}