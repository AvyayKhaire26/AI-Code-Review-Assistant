package com.codereview.ai_code_review.repository;

import com.codereview.ai_code_review.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    /**
     * Find all feedback for a specific review
     */
    List<Feedback> findByReviewId(Long reviewId);

    /**
     * Find all feedback by a specific user
     */
    List<Feedback> findByUserId(Long userId);

    /**
     * Count feedback for a review
     */
    long countByReviewId(Long reviewId);
}
