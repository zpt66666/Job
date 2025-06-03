package com.baiyun.javaee.controller;

import com.baiyun.javaee.common.ApiResponse;
import com.baiyun.javaee.model.InterviewQuestion;
import com.baiyun.javaee.repository.InterviewQuestionRepositoryInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/interview")
public class InterviewQuestionController {

    @Autowired
    private InterviewQuestionRepositoryInMemory interviewQuestionRepository;

    @GetMapping("/questions")
    public ApiResponse<List<InterviewQuestion>> getAllQuestions(@RequestParam(required = false) String category) {
        List<InterviewQuestion> questions;
        if (category != null && !category.isEmpty()) {
            questions = interviewQuestionRepository.findByCategory(category);
        } else {
            questions = interviewQuestionRepository.findAll();
        }
        return ApiResponse.success(questions);
    }

    @GetMapping("/categories")
    public ApiResponse<List<String>> getAllCategories() {
        List<String> categories = interviewQuestionRepository.findAll().stream()
                .map(InterviewQuestion::getCategory)
                .distinct()
                .collect(java.util.stream.Collectors.toList());
        return ApiResponse.success(categories);
    }
} 