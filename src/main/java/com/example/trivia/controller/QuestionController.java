package com.example.trivia.controller;

import com.example.trivia.model.CheckAnswersRequest;
import com.example.trivia.model.CheckAnswersResponse;
import com.example.trivia.service.TriviaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final TriviaService service;

    public QuestionController(TriviaService service) {
        this.service = service;
    }

    @GetMapping("/questions")
    public ResponseEntity<?> getQuestions(@RequestParam(defaultValue = "3") int amount) {
        try {
            Map<String, Object> resp = service.getQuestions(amount);
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("Something went wrong with retrieving questions", e.getMessage()));
        }
    }

    @PostMapping("/checkanswers")
    public ResponseEntity<?> checkAnswers(@RequestBody CheckAnswersRequest req) {
        try {
            CheckAnswersResponse res = service.checkAnswers(req);
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException ia) {
            return ResponseEntity.badRequest().body(Map.of("error", ia.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("Something went wrong with checking answers", e.getMessage()));
        }
    }
}
