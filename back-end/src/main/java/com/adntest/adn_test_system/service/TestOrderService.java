package com.adntest.adn_test_system.service;

import com.adntest.adn_test_system.dto.request.TestOrderRequest;
import com.adntest.adn_test_system.dto.response.TestOrderResponse;
import com.adntest.adn_test_system.entity.Account;
import com.adntest.adn_test_system.entity.TestOrder;
import com.adntest.adn_test_system.exception.AuthException;
import com.adntest.adn_test_system.repository.AccountRepository;
import com.adntest.adn_test_system.repository.SampleRepository;
import com.adntest.adn_test_system.repository.ServiceRepository;
import com.adntest.adn_test_system.repository.TestOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestOrderService {
    private final TestOrderRepository testOrderRepository;
    private final AccountRepository accountRepository;
    private final SampleRepository sampleRepository;
    private final ServiceRepository serviceRepository;

    private static final List<String> VALID_STATUSES = Arrays.asList(
            "pending", "confirmed", "in_progress", "completed", "cancelled", "rejected"
    );

    private static final List<String> VALID_ORDER_TYPES = Arrays.asList(
            "home_collection", "lab_visit", "mail_in"
    );

    public Page<TestOrderResponse> getAllTestOrders(Pageable pageable) {
        Page<TestOrder> testOrders = testOrderRepository.findAll(pageable);
        return testOrders.map(this::mapToDTO);
    }

    public TestOrderResponse getTestOrderById(String id) {
        TestOrder testOrder = testOrderRepository.findById(id)
                .orElseThrow(() -> new AuthException("Test order not found"));

        // Get current account from security context
        Account currentAccount = getCurrentAccount();


        return mapToDTO(testOrder);
    }

    public Page<TestOrderResponse> getMyTestOrders(Pageable pageable) {
        Account currentAccount = getCurrentAccount();
        if (currentAccount == null) {
            throw new AuthException("User not authenticated");
        }

        Page<TestOrder> testOrders = testOrderRepository.findByAccountUsername(currentAccount.getUsername(), pageable);
        return testOrders.map(this::mapToDTO);
    }

    public Page<TestOrderResponse> getTestOrdersByStatus(String status, Pageable pageable) {
        validateStatus(status);
        Page<TestOrder> testOrders = testOrderRepository.findByStatus(status, pageable);
        return testOrders.map(this::mapToDTO);
    }

    public TestOrderResponse createTestOrder(TestOrderRequest request) {
        String userId = request.getAccountId();
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new AuthException("Account not found"));

        validateOrderType(request.getOrderType());

        TestOrder newTestOrder = TestOrder.builder()
                .account(account)
                .sample(sampleRepository.findById(request.getSampleId())
                        .orElseThrow(() -> new AuthException("Sample not found")))
                .service(serviceRepository.findById(request.getServiceId())
                        .orElseThrow(() -> new AuthException("Service not found")))
                .orderType(request.getOrderType())
                .status("pending") // Default status
                .preferredDate(request.getPreferredDate())
                .note(request.getNote())
                .testOrderDate(LocalDateTime.now())
                .build();

        return mapToDTO(testOrderRepository.save(newTestOrder));
    }

    public TestOrderResponse updateTestOrder(String id, TestOrderRequest request) {
        TestOrder existingOrder = testOrderRepository.findById(id)
                .orElseThrow(() -> new AuthException("Test order not found"));

        if (request.getSampleId() != null) {
            existingOrder.setSample(sampleRepository.findById(request.getSampleId())
                    .orElseThrow(() -> new AuthException("Sample not found")));
        }

        if (request.getServiceId() != null) {
            existingOrder.setService(serviceRepository.findById(request.getServiceId())
                    .orElseThrow(() -> new AuthException("Service not found")));
        }

        if (request.getOrderType() != null) {
            validateOrderType(request.getOrderType());
            existingOrder.setOrderType(request.getOrderType());
        }

        if (request.getPreferredDate() != null) {
            existingOrder.setPreferredDate(request.getPreferredDate());
        }

        if (request.getNote() != null) {
            existingOrder.setNote(request.getNote());
        }

        return mapToDTO(testOrderRepository.save(existingOrder));
    }

    public TestOrderResponse updateTestOrderStatus(String id, String status) {
        validateStatus(status);
        TestOrder testOrder = testOrderRepository.findById(id)
                .orElseThrow(() -> new AuthException("Test order not found"));
        testOrder.setStatus(status);
        return mapToDTO(testOrderRepository.save(testOrder));
    }

    public void deleteTestOrder(String id) {
        TestOrder testOrder = testOrderRepository.findById(id)
                .orElseThrow(() -> new AuthException("Test order not found"));

        if (!"pending".equals(testOrder.getStatus()) && !"cancelled".equals(testOrder.getStatus())) {
            throw new AuthException("Cannot delete test order with status: " + testOrder.getStatus());
        }

        testOrderRepository.deleteById(id);
    }

    public boolean canUserModifyOrder(String orderId, String userId) {
        TestOrder testOrder = testOrderRepository.findById(orderId)
                .orElseThrow(() -> new AuthException("Test order not found"));
        return testOrder.getAccount().getUserId().equals(userId);
    }

    private TestOrderResponse mapToDTO(TestOrder testOrder) {
        return TestOrderResponse.builder()
                .testOrderId(testOrder.getTestOrderId())
                .accountId(testOrder.getAccount().getUserId())
                .sampleId(testOrder.getSample().getSampleId())
                .serviceId(testOrder.getService().getServiceId())
                .orderType(testOrder.getOrderType())
                .status(testOrder.getStatus())
                .testOrderDate(testOrder.getTestOrderDate())
                .preferredDate(testOrder.getPreferredDate())
                .note(testOrder.getNote())
                .build();
    }

    private void validateStatus(String status) {
        if (status == null || !VALID_STATUSES.contains(status.toLowerCase())) {
            throw new AuthException("Invalid status. Valid statuses: " + String.join(", ", VALID_STATUSES));
        }
    }

    private void validateOrderType(String orderType) {
        if (orderType == null || !VALID_ORDER_TYPES.contains(orderType.toLowerCase())) {
            throw new AuthException("Invalid order type. Valid types: " + String.join(", ", VALID_ORDER_TYPES));
        }
    }

    private Account getCurrentAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException("Current user not found"));
    }

    public List<String> getValidStatuses() {
        return VALID_STATUSES;
    }

    public List<String> getValidOrderTypes() {
        return VALID_ORDER_TYPES;
    }
}