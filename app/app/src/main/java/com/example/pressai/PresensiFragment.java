package com.example.pressai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.nfc.Tag;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ScanOptions;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PresensiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PresensiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CompoundBarcodeView barcodeView;

    @Nullable

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public PresensiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PresensiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PresensiFragment newInstance(String param1, String param2) {
        PresensiFragment fragment = new PresensiFragment();
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
        View view = inflater.inflate(R.layout.fragment_presensi,container,false);
        barcodeView = view.findViewById(R.id.qr_scanner);
        barcodeView.setStatusText("");
        SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new  String[]{Manifest.permission.CAMERA},1);}
        else{
            barcodeView.decodeSingle(new BarcodeCallback() {
                @Override
                public void barcodeResult(BarcodeResult result) {

                    String barcodeText = result.getText();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("sesi_kuliah");

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                String childName = childSnapshot.getKey();

                                if (Objects.equals(barcodeText, childName)) {
                                    Toast.makeText(getActivity(),childName,Toast.LENGTH_SHORT).show();
                                    DatabaseReference sesi_kuliah = FirebaseDatabase.getInstance().getReference("sesi_kuliah/"+childName+"/mata_kuliah_code");
                                    sesi_kuliah.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String mata_kuliah_code = snapshot.getValue(String.class);

                                            DatabaseReference mataKuliah = FirebaseDatabase.getInstance().getReference("mata_kuliah/"+mata_kuliah_code+"/mata_kuliah_name");
                                            mataKuliah.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    String mata_kuliah_name = snapshot.getValue(String.class);

                                                    DatabaseReference kehadiranRef = FirebaseDatabase.getInstance().getReference("kehadiran/" + childName);
                                                    String username = sharedPref.getString("username", "Mahasiswa");

                                                    DatabaseReference kehadiranUser = FirebaseDatabase.getInstance().getReference("users/" + username + "/kehadiran/" + childName);

                                                    // Check if the user has already been marked as present
                                                    kehadiranUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            if (snapshot.exists()) {
                                                                // If the user has already been marked as present, show a toast message and return
                                                                Toast.makeText(getActivity(),"Data sudah ada",Toast.LENGTH_SHORT).show();
                                                                return;
                                                            }

                                                            // Get the current date and time
                                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                                                            String currentDateAndTime = sdf.format(new Date());

                                                            // Get the session start and end times
                                                            String awal_waktu = childSnapshot.child("awal_waktu").getValue(String.class);
                                                            String akhir_waktu = childSnapshot.child("akhir_waktu").getValue(String.class);
                                                            String tanggal_sesi = childSnapshot.child("tanggal_sesi").getValue(String.class);

                                                            // Parse the current, start and end times to Date objects
                                                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                                                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                                            try {
                                                                Toast.makeText(getActivity(),currentDateAndTime,Toast.LENGTH_SHORT).show();
                                                                Date currentTime = timeFormat.parse(currentDateAndTime.split(" ")[1]);
                                                                Date startTime = timeFormat.parse(awal_waktu);
                                                                Date endTime = timeFormat.parse(akhir_waktu);

                                                                Date currentDate = dateFormat.parse(currentDateAndTime.split(" ")[0]);
                                                                Date sessionDate = dateFormat.parse(tanggal_sesi);

                                                                // Check if the current time is within the session time
                                                                if (currentTime.after(startTime) && currentTime.before(endTime)&& currentDate.equals(sessionDate)) {
                                                                    kehadiranRef.child(username).child("status_hadir").setValue("Hadir");
                                                                    kehadiranUser.child("status_hadir").setValue("Hadir");
                                                                    kehadiranUser.child("mata_kuliah_name").setValue(mata_kuliah_name);
                                                                    kehadiranUser.child("tanggal_sesi").setValue(currentDateAndTime);
                                                                    kehadiranRef.child(username).child("created_at").setValue(currentDateAndTime);

                                                                    Intent layout_berhasil = new Intent(getActivity().getApplicationContext(), BerhasilHadir.class);
                                                                    startActivity(layout_berhasil);
                                                                } else {
                                                                    Toast.makeText(getActivity(),"gagalhadir",Toast.LENGTH_SHORT).show();
                                                                }
                                                            } catch (ParseException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

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