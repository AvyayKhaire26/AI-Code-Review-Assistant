package com.codereview.ai_code_review.dto.response;

import java.util.Map;

public class UserStatsResponse {

    private Long totalReviews;
    private Long totalBugsFound;
    private Long criticalIssues;
    private Long highIssues;
    private Long mediumIssues;
    private Long lowIssues;
    private Map<String, Long> reviewsByLanguage;
    private Double averageProcessingTimeMs;

    // Constructors
    public UserStatsResponse() {}

    public UserStatsResponse(Long totalReviews, Long totalBugsFound, Long criticalIssues,
                             Long highIssues, Long mediumIssues, Long lowIssues,
                             Map<String, Long> reviewsByLanguage, Double averageProcessingTimeMs) {
        this.totalReviews = totalReviews;
        this.totalBugsFound = totalBugsFound;
        this.criticalIssues = criticalIssues;
        this.highIssues = highIssues;
        this.mediumIssues = mediumIssues;
        this.lowIssues = lowIssues;
        this.reviewsByLanguage = reviewsByLanguage;
        this.averageProcessingTimeMs = averageProcessingTimeMs;
    }

    // Getters and Setters
    public Long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Long totalReviews) {
        this.totalReviews = totalReviews;
    }

    public Long getTotalBugsFound() {
        return totalBugsFound;
    }

    public void setTotalBugsFound(Long totalBugsFound) {
        this.totalBugsFound = totalBugsFound;
    }

    public Long getCriticalIssues() {
        return criticalIssues;
    }

    public void setCriticalIssues(Long criticalIssues) {
        this.criticalIssues = criticalIssues;
    }

    public Long getHighIssues() {
        return highIssues;
    }

    public void setHighIssues(Long highIssues) {
        this.highIssues = highIssues;
    }

    public Long getMediumIssues() {
        return mediumIssues;
    }

    public void setMediumIssues(Long mediumIssues) {
        this.mediumIssues = mediumIssues;
    }

    public Long getLowIssues() {
        return lowIssues;
    }

    public void setLowIssues(Long lowIssues) {
        this.lowIssues = lowIssues;
    }

    public Map<String, Long> getReviewsByLanguage() {
        return reviewsByLanguage;
    }

    public void setReviewsByLanguage(Map<String, Long> reviewsByLanguage) {
        this.reviewsByLanguage = reviewsByLanguage;
    }

    public Double getAverageProcessingTimeMs() {
        return averageProcessingTimeMs;
    }

    public void setAverageProcessingTimeMs(Double averageProcessingTimeMs) {
        this.averageProcessingTimeMs = averageProcessingTimeMs;
    }
}
