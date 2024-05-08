package org.example.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Chat {

    public static String sendMessage(String s) {
        try {
            String response = getChatbotResponse("generate a comment on this topic " + s);
            return response ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public static String getChatbotResponse(String message) throws IOException, InterruptedException, JSONException {
        // Vos paramètres d'URL, clé API, etc.
        String url = "https://custom-chatbot-api.p.rapidapi.com/chatbotapi";
        String apiKey = "94ad36d3d4msh3a04edc3fd516bfp13c53ejsn98585183c67f";
        String botId = "OEXJ8qFp5E5AwRwymfPts90vrHnmr8yZgNE171101852010w2S0bCtN3THp448W7kDSfyTf3OpW5TUVefz";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "custom-chatbot-api.p.rapidapi.com")
                .POST(HttpRequest.BodyPublishers.ofString("{\r\n    \"bot_id\": \"" + botId + "\",\r\n    \"messages\": [\r\n        {\r\n            \"role\": \"user\",\r\n            \"content\": \"" + message + "\"\r\n        }\r\n    ],\r\n    \"user_id\": \"\",\r\n    \"temperature\": 0.9,\r\n    \"top_k\": 5,\r\n    \"top_p\": 0.9,\r\n    \"max_tokens\": 256,\r\n    \"model\": \"matag2.0\"\r\n}"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Extraire le message de la réponse JSON
        JSONObject jsonResponse = new JSONObject(response.body());
        String botResponse = jsonResponse.getString("result");

        return botResponse;
    }
}
