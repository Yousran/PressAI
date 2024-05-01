package com.example.pressai;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanTestFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private CompoundBarcodeView barcodeView;

    public ScanTestFragment() {
        // Required empty public constructor
    }

    public static ScanTestFragment newInstance(String param1, String param2) {
        ScanTestFragment fragment = new ScanTestFragment();
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
        View view = inflater.inflate(R.layout.fragment_scan_test,container,false);
        barcodeView = view.findViewById(R.id.qr_scanner);
        barcodeView.setStatusText("");
        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new  String[]{Manifest.permission.CAMERA},1);

        }else {
            barcodeView.decodeSingle(new BarcodeCallback() {
                @Override
                public void barcodeResult(BarcodeResult result) {
                    String barcodeText = result.getText();


                    DatabaseReference database_test = FirebaseDatabase.getInstance().getReference("test");

                    database_test.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                String childName = childSnapshot.getKey();

                                if (Objects.equals(barcodeText, childName)){
                                    String mata_kuliah_name = childSnapshot.child("mata_kuliah_name").getValue(String.class);
                                    String mata_kuliah_code = childSnapshot.child("mata_kuliah_code").getValue(String.class);
                                    String test_name = childSnapshot.child("test_name").getValue(String.class);
                                    String akhir_waktu = childSnapshot.child("akhir_waktu").getValue(String.class);
                                    String awal_waktu = childSnapshot.child("awal_waktu").getValue(String.class);
                                    String tanggal_test = childSnapshot.child("tanggal_test").getValue(String.class);
                                    String durasi = childSnapshot.child("durasi").getValue(String.class);

                                    Bundle bundle = new Bundle();
                                    bundle.putString("mata_kuliah_name", mata_kuliah_name);
                                    bundle.putString("mata_kuliah_code", mata_kuliah_code);
                                    bundle.putString("test_name", test_name);
                                    bundle.putString("test_code", childName);
                                    bundle.putString("akhir_waktu", akhir_waktu);
                                    bundle.putString("awal_waktu", awal_waktu);
                                    bundle.putString("tanggal_test", tanggal_test);
                                    bundle.putString("durasi", durasi);

                                    TestLobbyFragment testLobbyFragment = new TestLobbyFragment();
                                    testLobbyFragment.setArguments(bundle);

                                    // Replace the current fragment with the new one
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.main_frame, testLobbyFragment)
                                            .commit();
                                }
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle error here
                        }
                    });

                }
            });
        }

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeView.pause();
    }
}