package com.example.pressai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {

    private List<RiwayatItem> riwayatList;

    public RiwayatAdapter(List<RiwayatItem> riwayatList) {
        this.riwayatList = riwayatList;
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat, parent, false);
        return new RiwayatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatViewHolder holder, int position) {
        RiwayatItem riwayatItem = riwayatList.get(position);
        holder.textMataPelajaran.setText(riwayatItem.getMataPelajaran());
        holder.textTanggal.setText(riwayatItem.getTanggal());
        holder.textScore.setText("Score: " + riwayatItem.getScore());
    }

    @Override
    public int getItemCount() {
        return riwayatList.size();
    }

    public static class RiwayatViewHolder extends RecyclerView.ViewHolder {
        private TextView textMataPelajaran;
        private TextView textTanggal;
        private TextView textScore;

        public RiwayatViewHolder(@NonNull View itemView) {
            super(itemView);
            textMataPelajaran = itemView.findViewById(R.id.nama_matakuliah);
            textTanggal = itemView.findViewById(R.id.tanggal_sesi);
            textScore = itemView.findViewById(R.id.score);
        }
    }
}
