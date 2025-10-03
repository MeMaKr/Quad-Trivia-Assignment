package com.example.trivia;

import com.example.trivia.model.CheckAnswersRequest;
import com.example.trivia.model.CheckAnswersResponse;
import com.example.trivia.service.TriviaService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TriviaBackendApplicationTests {

    @Test
    void testCheckAnswers() {
        TriviaService service = new TriviaService(null);    // Dummy service

        String setId = "test-set";
        service.getStore().put(setId, Map.of("q0", "Answer A", "q1", "Answer B"));  // Dummy questions

        CheckAnswersRequest req = new CheckAnswersRequest(setId, Map.of(
                "q0", "Answer A",
                "q1", "Not answer A")     // Dummy answers
        );

        CheckAnswersResponse resp = service.checkAnswers(req);  // Check dummy answers

        assertEquals(2, resp.total());
        assertEquals(1, resp.correct());
        assertTrue(resp.detail().get("q0"));
        assertFalse(resp.detail().get("q1"));
    }

    @Test
    void testQuestionSetExpiresAfterCheck() {
        TriviaService service = new TriviaService(null);
        String setId = "test-set-2";
        service.getStore().put(setId, Map.of("q0", "X"));

        var resp = service.checkAnswers(new CheckAnswersRequest(setId, Map.of("q0", "X")));
        assertEquals(1, resp.correct());    // Check if it works

        assertThrows(IllegalArgumentException.class, () ->  // Check if it fails since set should be deleted
                service.checkAnswers(new CheckAnswersRequest(setId, Map.of("q0", "X")))
        );
    }
}
