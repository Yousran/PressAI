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
import java.util.Locale;

public class RiwayatUjianAdapter extends RecyclerView.Adapter<RiwayatUjianAdapter.RiwayatViewHolder> {

    Context context;
    private ArrayList<DataUjian> dataUjian;

    public static class RiwayatViewHolder extends RecyclerView.ViewHolder {
        TextView nama_matakuliah,test_score,tanggal_sesi;
        public RiwayatViewHolder(View itemView) {
            super(itemView);
            nama_matakuliah = itemView.findViewById(R.id.nama_matakuliah);
            tanggal_sesi = itemView.findViewById(R.id.tanggal_sesi);
            test_score = itemView.findViewById(R.id.score);
        }
    }

    public RiwayatUjianAdapter(Context context, ArrayList<DataUjian> dataUjian) {
        this.context = context;
        this.dataUjian = dataUjian;
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ujian, parent, false);
        return new RiwayatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RiwayatUjianAdapter.RiwayatViewHolder holder, int position) {
        DataUjian data = dataUjian.get(position);
        holder.nama_matakuliah.setText(data.getMata_kuliah_name());
        holder.tanggal_sesi.setText(data.getTanggal_test());
        holder.test_score.setText(String.format(Locale.getDefault(), "%.0f", data.getTest_score()));
    }

    @Override
    public int getItemCount() {
        return dataUjian.size();
    }
}
