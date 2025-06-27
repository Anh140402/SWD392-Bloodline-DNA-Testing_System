package com.adntest.adn_test_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adntest.adn_test_system.entity.Feedback;
import com.adntest.adn_test_system.entity.TestOrder;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    Page<Feedback> findByAccountUsername(String username, Pageable pageable);
    Page<Feedback> findByTestOrder(TestOrder testOrder, Pageable pageable);
    Page<Feedback> findByTestOrderTestOrderId(String testOrderId, Pageable pageable);
    Page<Feedback> findByRatingGreaterThanEqual(Integer rating, Pageable pageable);

    @Query("SELECT AVG(f.rating) FROM Feedback f")
    Double getAverageRating();

    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.rating = :rating")
    Long countByRating(@Param("rating") Integer rating);
}

