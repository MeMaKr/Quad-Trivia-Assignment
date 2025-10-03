package com.example.trivia.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Component
public class OpenTriviaClient {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    /*
    Fetches questions from Open Triva Database using endpoint and returns a list of mappings containing the questions,
    answers and possible choices.
     */
    public List<Map<String, Object>> fetchQuestions(int amount) throws Exception {
        String url = "https://opentdb.com/api.php?amount=" + amount + "&type=multiple";
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        JsonNode root = mapper.readTree(resp.body());
        JsonNode results = root.get("results");
        List<Map<String, Object>> out = new ArrayList<>();
        for (JsonNode r : results) {
            Map<String, Object> m = new HashMap<>();
            m.put("question", htmlDecode(r.get("question").asText()));
            m.put("correct", htmlDecode(r.get("correct_answer").asText()));
            List<String> choices = new ArrayList<>();
            for (JsonNode c : r.get("incorrect_answers")) choices.add(htmlDecode(c.asText()));
            choices.add(htmlDecode(r.get("correct_answer").asText()));
            Collections.shuffle(choices);
            m.put("choices", choices);
            out.add(m);
        }
        return out;
    }

    /*
    Helper function that translates html codes into normal symbols.
     */
    private String htmlDecode(String s) {
        return s.replaceAll("&quot;", "\"")
                .replaceAll("&amp;", "&")
                .replaceAll("&#039;", "'")
                .replaceAll("&eacute;", "é");
    }
}