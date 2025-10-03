package com.example.trivia.model;

import java.util.List;

public record QuestionDto(String id, String question, List<String> choices) {}