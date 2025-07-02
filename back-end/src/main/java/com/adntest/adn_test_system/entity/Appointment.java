package com.adntest.adn_test_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String appointmentId;

    private LocalDateTime scheduleDate;
    private String location;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
