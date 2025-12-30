package com.codereview.ai_code_review.service.Impl;

import com.codereview.ai_code_review.dto.request.CodeReviewRequest;
import com.codereview.ai_code_review.dto.response.ReviewResponse;
import com.codereview.ai_code_review.entity.Review;
import com.codereview.ai_code_review.entity.User;
import com.codereview.ai_code_review.exception.ResourceNotFoundException;
import com.codereview.ai_code_review.repository.ReviewRepository;
import com.codereview.ai_code_review.repository.UserRepository;
import com.codereview.ai_code_review.service.GeminiService;
import com.codereview.ai_code_review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.codereview.ai_code_review.dto.response.UserStatsResponse;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final GeminiService geminiService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             UserRepository userRepository,
                             GeminiService geminiService) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.geminiService = geminiService;
    }

    @Override
    public ReviewResponse createReview(CodeReviewRequest request, String username) {
        // Find user
        logger.info("Creating review for user: {}, language: {}", username, request.getLanguage());
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        // Start timing
        long startTime = System.currentTimeMillis();

        try {
            // Call Gemini API
            String aiResponse = geminiService.analyzeCode(
                    request.getCodeSnippet(),
                    request.getLanguage(),
                    request.getCustomPrompt()
            );

            // Calculate processing time
            long processingTime = System.currentTimeMillis() - startTime;
            logger.info("AI analysis completed in {}ms", processingTime);

            // Parse AI response and extract metrics
            int bugsFound = countOccurrences(aiResponse, "bug");
            int suggestionsCount = countOccurrences(aiResponse, "suggest");
            Review.Severity severity = determineSeverity(aiResponse);

            // Create review entity
            Review review = new Review();
            review.setUser(user);
            review.setCodeSnippet(request.getCodeSnippet());
            review.setLanguage(request.getLanguage());
            review.setCustomPrompt(request.getCustomPrompt());
            review.setAiResponse(aiResponse);
            review.setReviewSummary(extractSummary(aiResponse));
            review.setSeverity(severity);
            review.setBugsFound(bugsFound);
            review.setSuggestionsCount(suggestionsCount);
            review.setProcessingTimeMs(processingTime);

            // Save to database
            Review savedReview = reviewRepository.save(review);
            logger.info("Review saved with ID: {}", savedReview.getId());

            // Convert to response
            return convertToResponse(savedReview);
        } catch (Exception e) {
            logger.error("Error creating review for user :{}", username, e);
            throw new RuntimeException("Failed to create review: " + e.getMessage(), e);
        }
    }

    @Override
    public ReviewResponse getReviewById(Long reviewId, String username) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found: " + reviewId));

        // ✅ Check if this review belongs to the requesting user
        if (!review.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You don't have permission to view this review");
        }

        return convertToResponse(review);
    }


    @Override
    public List<ReviewResponse> getUserReviews(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        List<Review> reviews = reviewRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        return reviews.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long reviewId, String username) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found: " + reviewId));

        // Check if user owns this review
        if (!review.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You don't have permission to delete this review");
        }

        reviewRepository.delete(review);
    }

    // Helper methods

    private ReviewResponse convertToResponse(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setCodeSnippet(review.getCodeSnippet());
        response.setLanguage(review.getLanguage());
        response.setCustomPrompt(review.getCustomPrompt());
        response.setAiResponse(review.getAiResponse());
        response.setReviewSummary(review.getReviewSummary());
        response.setSeverity(review.getSeverity());
        response.setBugsFound(review.getBugsFound());
        response.setSuggestionsCount(review.getSuggestionsCount());
        response.setProcessingTimeMs(review.getProcessingTimeMs());
        response.setCreatedAt(review.getCreatedAt());
        response.setUserRating(review.getUserRating());
        return response;
    }

    private int countOccurrences(String text, String keyword) {
        String lowerText = text.toLowerCase();
        String lowerKeyword = keyword.toLowerCase();
        int count = 0;
        int index = 0;
        while ((index = lowerText.indexOf(lowerKeyword, index)) != -1) {
            count++;
            index += lowerKeyword.length();
        }
        return count;
    }

    private Review.Severity determineSeverity(String aiResponse) {
        String lower = aiResponse.toLowerCase();
        if (lower.contains("critical") || lower.contains("severe") ||
                lower.contains("sql injection") || lower.contains("security vulnerability")) {
            return Review.Severity.CRITICAL;
        } else if (lower.contains("high") || lower.contains("major") ||
                lower.contains("serious bug")) {
            return Review.Severity.HIGH;
        } else if (lower.contains("medium") || lower.contains("moderate")) {
            return Review.Severity.MEDIUM;
        } else {
            return Review.Severity.LOW;
        }
    }

    private String extractSummary(String aiResponse) {
        // Extract first 200 characters as summary
        if (aiResponse.length() > 200) {
            return aiResponse.substring(0, 200) + "...";
        }
        return aiResponse;
    }

    @Override
    public Page<ReviewResponse> getUserReviewsPaginated(
            String username,
            Review.Language language,
            Review.Severity severity,
            Pageable pageable) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        Page<Review> reviews = reviewRepository.findByUserIdWithFilters(
                user.getId(),
                language,
                severity,
                pageable
        );

        return reviews.map(this::convertToResponse);
    }

    @Override
    public UserStatsResponse getUserStats(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        List<Review> reviews = reviewRepository.findByUserIdOrderByCreatedAtDesc(user.getId());

        // Calculate statistics
        long totalReviews = reviews.size();
        long totalBugsFound = reviews.stream().mapToInt(Review::getBugsFound).sum();

        long criticalIssues = reviews.stream()
                .filter(r -> r.getSeverity() == Review.Severity.CRITICAL)
                .count();

        long highIssues = reviews.stream()
                .filter(r -> r.getSeverity() == Review.Severity.HIGH)
                .count();

        long mediumIssues = reviews.stream()
                .filter(r -> r.getSeverity() == Review.Severity.MEDIUM)
                .count();

        long lowIssues = reviews.stream()
                .filter(r -> r.getSeverity() == Review.Severity.LOW)
                .count();

        // Group by language
        Map<String, Long> reviewsByLanguage = reviews.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getLanguage().name(),
                        Collectors.counting()
                ));

        // Average processing time
        Double averageProcessingTime = reviews.stream()
                .mapToLong(Review::getProcessingTimeMs)
                .average()
                .orElse(0.0);

        return new UserStatsResponse(
                totalReviews,
                totalBugsFound,
                criticalIssues,
                highIssues,
                mediumIssues,
                lowIssues,
                reviewsByLanguage,
                averageProcessingTime
        );
    }
}
