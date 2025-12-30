package com.codereview.ai_code_review.repository;

import com.codereview.ai_code_review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Review> findByLanguage(Review.Language language);
    Long countByUserId(Long userId);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId " +
            "AND (:language IS NULL OR r.language = :language) " +
            "AND (:severity IS NULL OR r.severity = :severity) " +
            "ORDER BY r.createdAt DESC")
    Page<Review> findByUserIdWithFilters(
            @Param("userId") Long userId,
            @Param("language") Review.Language language,
            @Param("severity") Review.Severity severity,
            Pageable pageable
    );
}

