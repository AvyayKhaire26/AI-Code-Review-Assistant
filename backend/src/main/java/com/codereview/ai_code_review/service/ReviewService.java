package com.codereview.ai_code_review.service;

import com.codereview.ai_code_review.dto.request.CodeReviewRequest;
import com.codereview.ai_code_review.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.codereview.ai_code_review.entity.Review.Language;
import com.codereview.ai_code_review.entity.Review.Severity;
import com.codereview.ai_code_review.dto.response.UserStatsResponse;

import java.util.List;

public interface ReviewService {

    /**
     * Create a new code review using AI
     * @param request Code review request
     * @param username Username of the requester
     * @return Review response with AI analysis
     */
    ReviewResponse createReview(CodeReviewRequest request, String username);

    /**
     * Get review by ID
     * @param reviewId Review ID
     * @return Review response
     */
    ReviewResponse getReviewById(Long reviewId, String username);

    /**
     * Get all reviews for a user
     * @param username Username
     * @return List of review responses
     */
    List<ReviewResponse> getUserReviews(String username);
    

    /**
     * Delete a review
     * @param reviewId Review ID
     * @param username Username (for authorization)
     */

    Page<ReviewResponse> getUserReviewsPaginated(
            String username,
            Language language,
            Severity severity,
            Pageable pageable
    );

    /**
     * Get user statistics
     * @param username Username
     * @return User statistics
     */
    UserStatsResponse getUserStats(String username);

    void deleteReview(Long reviewId, String username);
}
