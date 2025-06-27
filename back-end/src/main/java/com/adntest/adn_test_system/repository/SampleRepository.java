package com.adntest.adn_test_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adntest.adn_test_system.entity.Sample;

@Repository
public interface SampleRepository extends JpaRepository<Sample, String> {
    Page<Sample> findBySampleType(String sampleType, Pageable pageable);
    
    @Query("SELECT DISTINCT s.sampleType FROM Sample s")
    Page<String> findDistinctSampleTypes(Pageable pageable);
}

