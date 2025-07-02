package com.adntest.adn_test_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "test_orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String testOrderId;

    @ManyToOne
    @JoinColumn(name = "sample_id")
    private Sample sample;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    private String appointmentId;

    private LocalDateTime testOrderDate;
    private LocalDateTime preferredDate;

    private String orderType; // "home_collection", etc.
    private String status;      // "pending", "confirmed", etc.
    private String note;
}
