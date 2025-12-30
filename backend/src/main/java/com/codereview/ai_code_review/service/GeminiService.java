package com.codereview.ai_code_review.service;

import com.codereview.ai_code_review.entity.Review;

public interface GeminiService {

    /**
     * Analyze code using Gemini AI
     * @param codeSnippet The code to review
     * @param language Programming language
     * @param customPrompt Optional custom prompt
     * @return AI-generated review response
     */
    String analyzeCode(String codeSnippet, Review.Language language, String customPrompt);
}
