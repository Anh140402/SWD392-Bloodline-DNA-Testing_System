package com.adntest.adn_test_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adntest.adn_test_system.entity.TestReport;
import com.adntest.adn_test_system.entity.TestOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestReportRepository extends JpaRepository<TestReport, String> {
    Optional<TestReport> findByTestOrder(TestOrder testOrder);
    Optional<TestReport> findByTestOrderTestOrderId(String testOrderId);
    List<TestReport> findByIsPositive(Boolean isPositive);
    
    @Query("SELECT tr FROM TestReport tr WHERE tr.createdAt >= :startDate AND tr.createdAt <= :endDate")
    List<TestReport> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                          @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT tr FROM TestReport tr ORDER BY tr.createdAt DESC")
    List<TestReport> findRecentReports();
    
    @Query("SELECT COUNT(tr) FROM TestReport tr WHERE tr.isPositive = true")
    Long countPositiveResults();
    
    @Query("SELECT COUNT(tr) FROM TestReport tr WHERE tr.isPositive = false")
    Long countNegativeResults();
}

