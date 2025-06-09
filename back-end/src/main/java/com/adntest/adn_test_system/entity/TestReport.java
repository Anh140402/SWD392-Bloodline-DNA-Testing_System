package com.adntest.adn_test_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "test_reports")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestReport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String testReportId;

    @OneToOne
    @JoinColumn(name = "request_id")
    private TestOrder testOrder;

    private String result;
    private LocalDateTime createdAt;
    private Boolean isPositive;
}
