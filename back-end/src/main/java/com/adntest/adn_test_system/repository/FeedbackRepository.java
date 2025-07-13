package com.adntest.adn_test_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adntest.adn_test_system.entity.Feedback;
import com.adntest.adn_test_system.entity.TestOrder;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    List<Feedback> findByAccountUsername(String username);
    List<Feedback> findByTestOrder(TestOrder testOrder);
    List<Feedback> findByTestOrderTestOrderId(String testOrderId);
    List<Feedback> findByRatingGreaterThanEqual(Integer minRating);

    @Query("SELECT AVG(f.rating) FROM Feedback f")
    Double getAverageRating();

    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.rating = :rating")
    Long countByRating(@Param("rating") Integer rating);
}

