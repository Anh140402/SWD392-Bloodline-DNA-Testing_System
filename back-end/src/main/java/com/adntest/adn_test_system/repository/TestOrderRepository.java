package com.adntest.adn_test_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adntest.adn_test_system.entity.TestOrder;
import com.adntest.adn_test_system.entity.Account;

@Repository
public interface TestOrderRepository extends JpaRepository<TestOrder, String> {
    Page<TestOrder> findByAccount(Account account, Pageable pageable);
    Page<TestOrder> findByStatus(String status, Pageable pageable);
    Page<TestOrder> findByAccountUsername(String username, Pageable pageable);
}

