package com.adntest.adn_test_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adntest.adn_test_system.entity.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, String> {
    Page<Service> findByServiceType(String serviceType, Pageable pageable);
    boolean existsByServiceName(String serviceName);
}

