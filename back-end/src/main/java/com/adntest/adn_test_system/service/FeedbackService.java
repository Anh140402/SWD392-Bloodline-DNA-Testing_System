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

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final AccountRepository accountRepository;
    private final TestOrderRepository testOrderRepository;

    public Page<FeedbackResponse> getAllFeedbacks(Pageable pageable) {
        return feedbackRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public FeedbackResponse getFeedbackById(String id, UserDetails userDetails) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new AuthException("Feedback not found"));
        if (!feedback.getAccount().getUserId().equals(userDetails.getUsername())) {
            throw new AuthException("Access denied");
        }
        return mapToResponse(feedback);
    }

    public FeedbackResponse createFeedback(FeedbackRequest request, UserDetails userDetails) {
        Account account = accountRepository.findById(userDetails.getUsername())
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

    public FeedbackResponse updateFeedback(String id, FeedbackRequest request, UserDetails userDetails) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new AuthException("Feedback not found"));
        if (!feedback.getAccount().getUserId().equals(userDetails.getUsername())) {
            throw new AuthException("Unauthorized");
        }

        feedback.setRating(request.getRating());
        feedback.setComment(request.getComment());

        return mapToResponse(feedbackRepository.save(feedback));
    }

    public void deleteFeedback(String id, UserDetails userDetails) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new AuthException("Feedback not found"));
        if (!feedback.getAccount().getUserId().equals(userDetails.getUsername())) {
            throw new AuthException("Unauthorized");
        }
        feedbackRepository.delete(feedback);
    }





    public Page<FeedbackResponse> getFeedbacksByMinimumRating(Integer minRating, Pageable pageable) {
        return feedbackRepository.findByRatingGreaterThanEqual(minRating, pageable)
                .map(this::mapToResponse);
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
