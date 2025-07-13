package com.adntest.adn_test_system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adntest.adn_test_system.entity.TestReport;
import com.adntest.adn_test_system.entity.TestOrder;
import com.adntest.adn_test_system.exception.AuthException;
import com.adntest.adn_test_system.repository.TestReportRepository;
import com.adntest.adn_test_system.repository.TestOrderRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestReportService {
    private final TestReportRepository testReportRepository;
    private final TestOrderRepository testOrderRepository;

    public List<TestReport> getAllTestReports() {
        return testReportRepository.findAll();
    }

    public TestReport getTestReportById(String id) {
        return testReportRepository.findById(id)
                .orElseThrow(() -> new AuthException("Test report not found"));
    }

    public Optional<TestReport> getTestReportByTestOrder(TestOrder testOrder) {
        return testReportRepository.findByTestOrder(testOrder);
    }

    public Optional<TestReport> getTestReportByTestOrderId(String testOrderId) {
        return testReportRepository.findByTestOrderTestOrderId(testOrderId);
    }

    public List<TestReport> getTestReportsByResult(Boolean isPositive) {
        return testReportRepository.findByIsPositive(isPositive);
    }

    public List<TestReport> getTestReportsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return testReportRepository.findByCreatedAtBetween(startDate, endDate);
    }

    public List<TestReport> getRecentTestReports() {
        return testReportRepository.findRecentReports();
    }

    public Long getPositiveResultsCount() {
        return testReportRepository.countPositiveResults();
    }

    public Long getNegativeResultsCount() {
        return testReportRepository.countNegativeResults();
    }

    public TestReport createTestReport(TestReport testReport, String testOrderId) {
        TestOrder testOrder = testOrderRepository.findById(testOrderId)
                .orElseThrow(() -> new AuthException("Test order not found"));
        
        // Check if report already exists for this test order
        if (testReportRepository.findByTestOrder(testOrder).isPresent()) {
            throw new AuthException("Test report already exists for this test order");
        }
        
        // Validate test order status
        if (!"completed".equals(testOrder.getStatus()) && !"in_progress".equals(testOrder.getStatus())) {
            throw new AuthException("Cannot create report for test order with status: " + testOrder.getStatus());
        }
        
        // Validate required fields
        if (testReport.getResult() == null || testReport.getResult().trim().isEmpty()) {
            throw new AuthException("Test result cannot be empty");
        }
        
        if (testReport.getIsPositive() == null) {
            throw new AuthException("Test result status (positive/negative) must be specified");
        }
        
        TestReport newTestReport = TestReport.builder()
                .testOrder(testOrder)
                .result(testReport.getResult())
                .isPositive(testReport.getIsPositive())
                .createdAt(LocalDateTime.now())
                .build();
        
        TestReport savedReport = testReportRepository.save(newTestReport);
        
        // Update test order status to completed if it's in progress
        if ("in_progress".equals(testOrder.getStatus())) {
            testOrder.setStatus("completed");
            testOrderRepository.save(testOrder);
        }
        
        return savedReport;
    }

    public TestReport updateTestReport(String id, TestReport testReport) {
        TestReport existingReport = getTestReportById(id);
        
        // Validate required fields
        if (testReport.getResult() != null) {
            if (testReport.getResult().trim().isEmpty()) {
                throw new AuthException("Test result cannot be empty");
            }
            existingReport.setResult(testReport.getResult());
        }
        
        if (testReport.getIsPositive() != null) {
            existingReport.setIsPositive(testReport.getIsPositive());
        }
        
        // Note: We don't update createdAt or testOrder for integrity reasons
        
        return testReportRepository.save(existingReport);
    }

    public void deleteTestReport(String id) {
        TestReport testReport = getTestReportById(id);
        
        // Update associated test order status back to in_progress if deleting the report
        TestOrder testOrder = testReport.getTestOrder();
        if ("completed".equals(testOrder.getStatus())) {
            testOrder.setStatus("in_progress");
            testOrderRepository.save(testOrder);
        }
        
        testReportRepository.deleteById(id);
    }

    public boolean canUserAccessReport(String reportId, String userId) {
        TestReport testReport = getTestReportById(reportId);
        return testReport.getTestOrder().getAccount().getUserId().equals(userId);
    }

    public double getPositiveResultPercentage() {
        Long totalReports = testReportRepository.count();
        if (totalReports == 0) {
            return 0.0;
        }
        Long positiveReports = getPositiveResultsCount();
        return (positiveReports.doubleValue() / totalReports.doubleValue()) * 100.0;
    }
}

