package com.example.trivia.model;

import java.util.Map;

public record CheckAnswersRequest(String questionSetId, Map<String, String> answers) {}