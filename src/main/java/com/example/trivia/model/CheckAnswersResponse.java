package com.example.trivia.model;

import java.util.Map;

public record CheckAnswersResponse(int total, int correct, Map<String, Boolean> detail) {}