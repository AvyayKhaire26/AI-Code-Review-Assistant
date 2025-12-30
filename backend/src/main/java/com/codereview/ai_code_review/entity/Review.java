package com.codereview.ai_code_review.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "code_snippet", columnDefinition = "TEXT", nullable = false)
    private String codeSnippet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Column(name = "custom_prompt", columnDefinition = "TEXT")
    private String customPrompt;

    @Column(name = "ai_response", columnDefinition = "TEXT", nullable = false)
    private String aiResponse;

    @Column(name = "review_summary", columnDefinition = "TEXT")
    private String reviewSummary;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Column(name = "bugs_found")
    private Integer bugsFound = 0;

    @Column(name = "suggestions_count")
    private Integer suggestionsCount = 0;

    @Column(name = "processing_time_ms")
    private Long processingTimeMs;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "user_rating")
    private Integer userRating;

    // Constructors
    public Review() {}

    public Review(Long id, User user, String codeSnippet, Language language,
                  String customPrompt, String aiResponse, String reviewSummary,
                  Severity severity, Integer bugsFound, Integer suggestionsCount,
                  Long processingTimeMs, LocalDateTime createdAt, Integer userRating) {
        this.id = id;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCodeSnippet() {
        return codeSnippet;
    }

    public void setCodeSnippet(String codeSnippet) {
        this.codeSnippet = codeSnippet;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
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

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
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

    // Enums
    public enum Language {
        PYTHON, JAVA, JAVASCRIPT, TYPESCRIPT, CPP, C, GO, RUBY, SQL, OTHER
    }

    public enum Severity {
        LOW, MEDIUM, HIGH, CRITICAL
    }
}
