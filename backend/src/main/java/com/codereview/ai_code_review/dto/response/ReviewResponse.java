package com.codereview.ai_code_review.dto.response;

import com.codereview.ai_code_review.entity.Review;

import java.time.LocalDateTime;

public class ReviewResponse {

    private Long id;
    private String codeSnippet;
    private Review.Language language;
    private String customPrompt;
    private String aiResponse;
    private String reviewSummary;
    private Review.Severity severity;
    private Integer bugsFound;
    private Integer suggestionsCount;
    private Long processingTimeMs;
    private LocalDateTime createdAt;
    private Integer userRating;

    // Constructors
    public ReviewResponse() {}

    public ReviewResponse(Long id, String codeSnippet, Review.Language language, String customPrompt,
                          String aiResponse, String reviewSummary, Review.Severity severity,
                          Integer bugsFound, Integer suggestionsCount, Long processingTimeMs,
                          LocalDateTime createdAt, Integer userRating) {
        this.id = id;
        this.codeSnippet = codeSnippet;
        this.language = language;
        this.customPrompt = customPrompt;
        this.aiResponse = aiResponse;
        this.reviewSummary = reviewSummary;
        this.severity = severity;
        this.bugsFound = bugsFound;
        this.suggestionsCount = suggestionsCount;
        this.processingTimeMs = processingTimeMs;
        this.createdAt = createdAt;
        this.userRating = userRating;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeSnippet() {
        return codeSnippet;
    }

    public void setCodeSnippet(String codeSnippet) {
        this.codeSnippet = codeSnippet;
    }

    public Review.Language getLanguage() {
        return language;
    }

    public void setLanguage(Review.Language language) {
        this.language = language;
    }

    public String getCustomPrompt() {
        return customPrompt;
    }

    public void setCustomPrompt(String customPrompt) {
        this.customPrompt = customPrompt;
    }

    public String getAiResponse() {
        return aiResponse;
    }

    public void setAiResponse(String aiResponse) {
        this.aiResponse = aiResponse;
    }

    public String getReviewSummary() {
        return reviewSummary;
    }

    public void setReviewSummary(String reviewSummary) {
        this.reviewSummary = reviewSummary;
    }

    public Review.Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Review.Severity severity) {
        this.severity = severity;
    }

    public Integer getBugsFound() {
        return bugsFound;
    }

    public void setBugsFound(Integer bugsFound) {
        this.bugsFound = bugsFound;
    }

    public Integer getSuggestionsCount() {
        return suggestionsCount;
    }

    public void setSuggestionsCount(Integer suggestionsCount) {
        this.suggestionsCount = suggestionsCount;
    }

    public Long getProcessingTimeMs() {
        return processingTimeMs;
    }

    public void setProcessingTimeMs(Long processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUserRating() {
        return userRating;
    }

    public void setUserRating(Integer userRating) {
        this.userRating = userRating;
    }
}
