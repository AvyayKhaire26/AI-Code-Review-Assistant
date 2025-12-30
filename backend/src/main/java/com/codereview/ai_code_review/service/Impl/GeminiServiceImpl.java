package com.codereview.ai_code_review.service.Impl;

import com.codereview.ai_code_review.entity.Review;
import com.codereview.ai_code_review.service.GeminiService;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiServiceImpl implements GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private Client geminiClient;

    // Initialize client after Spring injects the API key
    private Client getClient() {
        if (geminiClient == null) {
            geminiClient = Client.builder()
                    .apiKey(apiKey)
                    .build();
        }
        return geminiClient;
    }

    @Override
    public String analyzeCode(String codeSnippet, Review.Language language, String customPrompt) {
        try {
            // Build the prompt
            String prompt = buildPrompt(codeSnippet, language, customPrompt);

            // Call Gemini using official SDK
            GenerateContentResponse response = getClient().models.generateContent(
                    "gemini-2.5-flash",
                    prompt,
                    null
            );

            // Extract text from response
            return response.text();

        } catch (Exception e) {
            throw new RuntimeException("Failed to analyze code with Gemini: " + e.getMessage(), e);
        }
    }

    private String buildPrompt(String codeSnippet, Review.Language language, String customPrompt) {
        String languageName = language.name().toLowerCase();

        if (customPrompt != null && !customPrompt.trim().isEmpty()) {
            // User provided custom prompt
            return "You are an expert code reviewer. Analyze the following " +
                    languageName + " code and answer this question: " +
                    customPrompt + "\n\nCode:\n" +
                    codeSnippet;
        } else {
            // Default comprehensive review
            return "You are an expert code reviewer. Analyze the following " +
                    languageName + " code and provide a comprehensive review.\n\n" +
                    "Focus on:\n" +
                    "1. BUGS: Logic errors, edge cases, potential crashes\n" +
                    "2. SECURITY: Vulnerabilities, injection risks, unsafe operations\n" +
                    "3. PERFORMANCE: Inefficiencies, optimization opportunities\n" +
                    "4. BEST PRACTICES: Code quality, naming, design patterns\n" +
                    "5. IMPROVEMENTS: Suggested refactored code\n\n" +
                    "Code:\n" + codeSnippet + "\n\n" +
                    "Provide specific, actionable feedback with code examples where applicable.";
        }
    }
}
