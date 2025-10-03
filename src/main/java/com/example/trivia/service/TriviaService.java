package com.example.trivia.service;

import com.example.trivia.model.QuestionDto;
import com.example.trivia.model.CheckAnswersRequest;
import com.example.trivia.model.CheckAnswersResponse;
import com.example.trivia.util.OpenTriviaClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TriviaService {

    private final OpenTriviaClient client;
    private final Map<String, Map<String, String>> store = new ConcurrentHashMap<>();

    public TriviaService(OpenTriviaClient client) {
        this.client = client;
    }

    public Map<String, Map<String, String>> getStore() {
        return store;
    }

    /*
    Transforms the data received from the client into a format that has no information about the correct answer
    (QuestionDto), which is returned in the response. The correct answers are put in the store.
     */
    public Map<String, Object> getQuestions(int amount) throws Exception {
        var raw = client.fetchQuestions(amount);
        String setId = UUID.randomUUID().toString();
        Map<String, String> answers = new HashMap<>();
        List<QuestionDto> questions = new ArrayList<>();
        int idx = 0;
        for (var q : raw) {
            String qid = "q" + idx++;
            @SuppressWarnings("unchecked")
            List<String> choices = (List<String>) q.get("choices");
            questions.add(new QuestionDto(qid, (String) q.get("question"), choices));
            answers.put(qid, (String) q.get("correct"));
        }

        store.put(setId, answers);
        Map<String, Object> resp = new HashMap<>();
        resp.put("questionSetId", setId);
        resp.put("questions", questions);
        return resp;
    }

    /*
    Retrieves the correct answers from the store and compares them to the user answers. Returns which and how many
    questions were answered correctly.
     */
    public CheckAnswersResponse checkAnswers(CheckAnswersRequest req) {
        Map<String, String> correct = store.remove(req.questionSetId());
        if (correct == null) throw new IllegalArgumentException("Invalid or expired questionSetId");

        Map<String, Boolean> detail = new HashMap<>();
        int correctCount = 0;
        for (var entry : req.answers().entrySet()) {
            String qid = entry.getKey();
            String given = entry.getValue();
            boolean userAnswerIsCorrect = given != null && given.equals(correct.get(qid));
            detail.put(qid, userAnswerIsCorrect);
            if (userAnswerIsCorrect) correctCount++;
        }
        return new CheckAnswersResponse(correct.size(), correctCount, detail);
    }
}