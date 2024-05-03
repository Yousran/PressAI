package com.example.pressai;

public class RiwayatItem {
    private String mataPelajaran;
    private String tanggal;
    private int score; // Ubah tipe data menjadi int

    public RiwayatItem(String mataPelajaran, String tanggal, int score) {
        this.mataPelajaran = mataPelajaran;
        this.tanggal = tanggal;
        this.score = score;
    }

    // Getter dan setter
    public String getMataPelajaran() {
        return mataPelajaran;
    }

    public void setMataPelajaran(String mataPelajaran) {
        this.mataPelajaran = mataPelajaran;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

