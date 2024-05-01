package com.example.pressai;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPTAPI {
    public static String chatGPT(String jawaban, String pertanyaan) throws IOException {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "sk-proj-ZhRjfjbRWGxpBzfVEXs3T3BlbkFJepKrlmwySw5PqYQPvo8N"; // API key goes here
        String model = "gpt-3.5-turbo"; // current model of chatgpt api

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            String prompt = "berikan nilai dari 1 sampai 5 terhadap jawaban atas pertanyaan berikut ";


            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"system\", \"content\": \"" + prompt + "\"}, {\"role\": \"user\", \"content\": \"" + pertanyaan + " Dengan Jawaban " + jawaban +"\"}]}";
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

    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content") + 11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }

}

