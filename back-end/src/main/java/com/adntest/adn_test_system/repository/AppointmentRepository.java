package com.adntest.adn_test_system.repository;

import com.adntest.adn_test_system.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    // If your TestOrder entity has a field named "testOrderId"
    Optional<Appointment> findByTestOrderTestOrderId(String testOrderId);

    // OR if you want to keep the method name findByTestOrderId, use @Query
    @Query("SELECT a FROM Appointment a WHERE a.testOrder.testOrderId = :testOrderId")
    Optional<Appointment> findByTestOrderId(@Param("testOrderId") String testOrderId);
}

