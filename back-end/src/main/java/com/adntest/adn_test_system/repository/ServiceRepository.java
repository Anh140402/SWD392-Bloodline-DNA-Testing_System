package com.adntest.adn_test_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adntest.adn_test_system.entity.Service;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, String> {
    List<Service> findByServiceType(String serviceType);
    boolean existsByServiceName(String serviceName);
}

