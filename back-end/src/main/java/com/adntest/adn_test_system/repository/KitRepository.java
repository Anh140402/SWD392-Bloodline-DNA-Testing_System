package com.adntest.adn_test_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adntest.adn_test_system.entity.Kit;
import com.adntest.adn_test_system.entity.TestOrder;

import java.util.Optional;

@Repository
public interface KitRepository extends JpaRepository<Kit, String> {
    Optional<Kit> findByCode(String code);
    Optional<Kit> findByTestOrder(TestOrder testOrder);
    Optional<Kit> findByTestOrderTestOrderId(String testOrderId);
    boolean existsByCode(String code);

    boolean existsByTestOrder(TestOrder testOrder);

}

