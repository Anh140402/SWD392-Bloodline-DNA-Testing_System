package com.adntest.adn_test_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "kits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Kit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String kitId;

    private String code;
    private LocalDateTime issuedAt;

    @OneToOne
    @JoinColumn(name = "request_id")
    private TestOrder testOrder;
}
