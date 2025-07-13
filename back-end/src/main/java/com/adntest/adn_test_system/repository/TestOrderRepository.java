package com.adntest.adn_test_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adntest.adn_test_system.entity.TestOrder;
import com.adntest.adn_test_system.entity.Account;
import java.util.List;

@Repository
public interface TestOrderRepository extends JpaRepository<TestOrder, String> {
    Page<TestOrder> findByAccount(Account account, Pageable pageable);
    Page<TestOrder> findByStatus(String status, Pageable pageable);
    List<TestOrder> findByAccountUsername(String username);
    List<TestOrder> findByStatus(String status);
}

