package com.example.pressai;

import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPTAPI {
    public static String chatGPT(String jawaban, String pertanyaan, String kunci_jawaban) throws IOException {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "sk-proj-LwggG20V6q8JXsE1dwYdT3BlbkFJenNcaNgQjnwq75kJOU5H";
        String model = "gpt-3.5-turbo-0125";

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            String prompt = "Berikan penilaian dari 1 sampai 5 berdasarkan relevansi jawaban dengan kunci jawaban. Skala penilaian adalah sebagai berikut: 1 jawaban tidak ada atau tidak tahu, 2 sangat tidak relevan, 3 kurang relevan, 4 cukup relevan, dan 5 relevan.";

            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"system\", \"content\": \"" + prompt +" Pertanyaan: " + "["+ pertanyaan + "]"+ " dan kunci jawaban : ["+ kunci_jawaban + "]"+ "\"}, {\"role\": \"user\", \"content\": \"" + " Dengan Jawaban: ["+ prepareAnswerForAPI(jawaban) + "]"+" berikan penilaian hanya berupa angka 1 sampai 5 tanpa adanya komentar tambahan"+"\"}]}";
            Log.d("Isi Json", "Isi Body: " + body);

            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();


            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return extractContentFromResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String prepareAnswerForAPI(String jawaban) {
        if (jawaban.trim().isEmpty()) {
            return "tidak tahu";
        }
        return jawaban;
    }

    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content") + 11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }

}

