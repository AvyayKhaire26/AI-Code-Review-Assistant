package com.codereview.ai_code_review.controller;

import com.codereview.ai_code_review.dto.request.CodeReviewRequest;
import com.codereview.ai_code_review.dto.response.ApiResponse;
import com.codereview.ai_code_review.dto.response.ReviewResponse;
import com.codereview.ai_code_review.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.codereview.ai_code_review.entity.Review;
import com.codereview.ai_code_review.dto.response.UserStatsResponse;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Create a new code review
     * POST /api/reviews
     * Requires JWT authentication
     */
    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            @Valid @RequestBody CodeReviewRequest request,
            Authentication authentication) {

        String username = authentication.getName();
        ReviewResponse response = reviewService.createReview(request, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all reviews for the authenticated user
     * GET /api/reviews/my-reviews
     * Requires JWT authentication
     */
    @GetMapping("/my-reviews")
    public ResponseEntity<List<ReviewResponse>> getMyReviews(Authentication authentication) {
        String username = authentication.getName();
        List<ReviewResponse> reviews = reviewService.getUserReviews(username);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get paginated and filtered reviews
     * GET /api/reviews?page=0&size=10&language=JAVA&severity=HIGH
     */
    @GetMapping
    public ResponseEntity<Page<ReviewResponse>> getReviewsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Review.Language language,
            @RequestParam(required = false) Review.Severity severity,
            Authentication authentication) {

        String username = authentication.getName();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<ReviewResponse> reviews = reviewService.getUserReviewsPaginated(
                username,
                language,
                severity,
                pageable
        );

        return ResponseEntity.ok(reviews);
    }

    /**
     * Get user statistics
     * GET /api/reviews/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<UserStatsResponse> getUserStats(Authentication authentication) {
        String username = authentication.getName();
        UserStatsResponse stats = reviewService.getUserStats(username);
        return ResponseEntity.ok(stats);
    }

    /**
     * Delete a review
     * DELETE /api/reviews/{id}
     * Requires JWT authentication
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteReview(
            @PathVariable Long id,
            Authentication authentication) {

        String username = authentication.getName();
        reviewService.deleteReview(id, username);
        return ResponseEntity.ok(ApiResponse.success("Review deleted successfully"));
    }

    /**
     * Get a specific review by ID
     * GET /api/reviews/{id}
     * Requires JWT authentication
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(
            @PathVariable Long id,
            Authentication authentication) {  // ✅ Add authentication

        String username = authentication.getName();
        ReviewResponse response = reviewService.getReviewById(id, username);
        return ResponseEntity.ok(response);
    }
}
