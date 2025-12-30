package com.codereview.ai_code_review.dto.request;

import com.codereview.ai_code_review.entity.Review;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CodeReviewRequest {

    @NotBlank(message = "Code snippet is required")
    @Size(max = 10000, message = "Code snippet must not exceed 10,000 characters")
    private String codeSnippet;

    @NotNull(message = "Language is required")
    private Review.Language language;

    @Size(max = 500, message = "Custom prompt must not exceed 500 characters")
    private String customPrompt;

    // Getters and Setters
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
}
