package com.adntest.adn_test_system.service;

import com.adntest.adn_test_system.dto.request.FeedbackRequest;
import com.adntest.adn_test_system.dto.response.FeedbackResponse;
import com.adntest.adn_test_system.entity.Account;
import com.adntest.adn_test_system.entity.Feedback;
import com.adntest.adn_test_system.entity.TestOrder;
import com.adntest.adn_test_system.exception.AuthException;
import com.adntest.adn_test_system.repository.AccountRepository;
import com.adntest.adn_test_system.repository.FeedbackRepository;
import com.adntest.adn_test_system.repository.TestOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final AccountRepository accountRepository;
    private final TestOrderRepository testOrderRepository;

    public List<FeedbackResponse> getAllFeedbacks() {
        return feedbackRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public FeedbackResponse getFeedbackById(String id, String userId) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new AuthException("Feedback not found"));
        // No authorization check
        return mapToResponse(feedback);
    }

    public FeedbackResponse createFeedback(FeedbackRequest request, String userId) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));
        TestOrder testOrder = testOrderRepository.findById(request.getTestOrderId())
                .orElseThrow(() -> new AuthException("Test order not found"));
        Feedback feedback = Feedback.builder()
                .account(account)
                .testOrder(testOrder)
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .build();
        return mapToResponse(feedbackRepository.save(feedback));
    }

    public FeedbackResponse updateFeedback(String id, FeedbackRequest request, String userId) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new AuthException("Feedback not found"));
        // No authorization check
        feedback.setRating(request.getRating());
        feedback.setComment(request.getComment());
        return mapToResponse(feedbackRepository.save(feedback));
    }

    public void deleteFeedback(String id, String userId) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new AuthException("Feedback not found"));
        // No authorization check
        feedbackRepository.delete(feedback);
    }





    public List<FeedbackResponse> getFeedbacksByMinimumRating(Integer minRating) {
        return feedbackRepository.findByRatingGreaterThanEqual(minRating)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }



    private FeedbackResponse mapToResponse(Feedback feedback) {
        return FeedbackResponse.builder()
                .feedbackId(feedback.getFeedbackId())
                .rating(feedback.getRating())
                .comment(feedback.getComment())
                .accountId(feedback.getAccount().getUserId())
                .testOrderId(feedback.getTestOrder().getTestOrderId())
                .createdAt(feedback.getCreatedAt())
                .build();
    }
}
