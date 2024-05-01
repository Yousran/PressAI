package com.example.pressai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class KehadiranAdapter extends RecyclerView.Adapter<KehadiranAdapter.MyViewHolder> {

    Context context;
    private ArrayList<DataKehadiran> dataKehadiran;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama_matakuliah,status_kehadiran,tanggal_sesi;
        public MyViewHolder(View itemView) {
            super(itemView);
            nama_matakuliah = itemView.findViewById(R.id.nama_matakuliah);
            status_kehadiran = itemView.findViewById(R.id.status_kehadiran);
            tanggal_sesi = itemView.findViewById(R.id.tanggal_sesi);
        }
    }

    public KehadiranAdapter(Context context, ArrayList<DataKehadiran> dataKehadiran) {
        this.context = context;
        this.dataKehadiran = dataKehadiran;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_kehadiran, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DataKehadiran data = dataKehadiran.get(position);
        holder.nama_matakuliah.setText(data.getMata_kuliah_name());
        holder.tanggal_sesi.setText(data.getTanggal_sesi());
        holder.status_kehadiran.setText(data.getStatus_hadir());
    }

    @Override
    public int getItemCount() {
        return dataKehadiran.size();
    }
}

