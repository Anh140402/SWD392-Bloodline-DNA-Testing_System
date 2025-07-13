package com.adntest.adn_test_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adntest.adn_test_system.entity.Sample;

import java.util.List;

@Repository
public interface SampleRepository extends JpaRepository<Sample, String> {
    List<Sample> findBySampleType(String sampleType);
    
    @Query("SELECT DISTINCT s.sampleType FROM Sample s")
    List<String> findDistinctSampleTypes();
}

