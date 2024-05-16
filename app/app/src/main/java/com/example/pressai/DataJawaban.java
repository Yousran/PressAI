package com.example.pressai;

import android.os.Parcel;
import android.os.Parcelable;

public class DataJawaban implements Parcelable {
    public String username;
    public String pertanyaan;
    public String jawaban;
    public String kunci_jawaban;
    public String soal_code;
    public String test_code;
    public int skor = 0;

    public DataJawaban() {
        // Default constructor
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }
    public String getKunci_jawaban() {
        return kunci_jawaban;
    }

    public void setKunci_jawaban(String kunci_jawaban) {
        this.kunci_jawaban = kunci_jawaban;
    }

    public String getSoal_code() {
        return soal_code;
    }

    public String getTest_code() {
        return test_code;
    }

    public int getSkor() {
        return skor;
    }

    // Parcelable implementation
    protected DataJawaban(Parcel in) {
        username = in.readString();
        pertanyaan = in.readString();
        jawaban = in.readString();
        kunci_jawaban = in.readString();
        soal_code = in.readString();
        test_code = in.readString();
        skor = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(pertanyaan);
        dest.writeString(jawaban);
        dest.writeString(kunci_jawaban);
        dest.writeString(soal_code);
        dest.writeString(test_code);
        dest.writeInt(skor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<DataJawaban> CREATOR = new Parcelable.Creator<DataJawaban>() {
        @Override
        public DataJawaban createFromParcel(Parcel in) {
            return new DataJawaban(in);
        }

        @Override
        public DataJawaban[] newArray(int size) {
            return new DataJawaban[size];
        }
    };
}
