package bukc.project;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class GeminiApi {

    private static final String API_KEY = "AIzaSyC4YRMrFh9o21QHOmJ1fQlF1EKLiQ2Prq0"; // Replace with your API key
    private static final String API_URL =
    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;


    public static String getResponse(String userInput) {
        try {
            String jsonInput = """
            {
              "contents": [{
                "parts": [{"text": "%s"}]
              }]
            }
            """.formatted(userInput);

            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);

            InputStream inputStream = (responseCode >= 200 && responseCode < 300)
                ? connection.getInputStream()
                : connection.getErrorStream();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                System.out.println("Raw JSON Response:");
                System.out.println(response.toString());

                if (responseCode >= 200 && responseCode < 300) {
                    return extractResponseText(response.toString());
                } else {
                    return "API Error: " + response.toString();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "An exception occurred: " + e.getMessage();
        }
    }

    private static String extractResponseText(String jsonResponse) {
    try {
        JSONObject obj = new JSONObject(jsonResponse);
        JSONArray candidates = obj.getJSONArray("candidates");
        if (candidates.length() > 0) {
            JSONObject content = candidates.getJSONObject(0).getJSONObject("content");
            JSONArray parts = content.getJSONArray("parts");
            if (parts.length() > 0) {
                String text = parts.getJSONObject(0).getString("text");

                // Clean up response formatting
                text = text.replace("\\n", "\n");             // Proper newlines
                text = text.replaceAll("\\*\\*(.*?)\\*\\*", "$1"); // Remove bold markdown (**text** -> text)
                text = text.replaceAll("(?i)camila.*?dimal\\.", ""); // Remove hallucinated sentence

                return text.trim();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "Could not parse Gemini response.";
}

}
