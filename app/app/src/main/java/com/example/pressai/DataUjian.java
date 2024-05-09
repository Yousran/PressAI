package com.example.pressai;

public class DataUjian {
    private String mata_kuliah_name;
    private String tanggal_test;
    private double test_score;

    public DataUjian(){

    }

    public String getMata_kuliah_name() {
        return mata_kuliah_name;
    }

    public void setMata_kuliah_name(String mata_kuliah_name) {
        this.mata_kuliah_name = mata_kuliah_name;
    }

    public String getTanggal_test() {
        return tanggal_test;
    }

    public void setTanggal_test(String tanggal_test) {
        this.tanggal_test = tanggal_test;
    }

    public double getTest_score() {
        return test_score;
    }

    public void setTest_score(double test_score) {
        this.test_score = test_score;
    }
}

