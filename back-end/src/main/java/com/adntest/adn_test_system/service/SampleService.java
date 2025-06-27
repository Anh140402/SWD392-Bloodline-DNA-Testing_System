package com.adntest.adn_test_system.service;

import com.adntest.adn_test_system.dto.request.SampleRequest;
import com.adntest.adn_test_system.dto.response.SampleResponse;
import com.adntest.adn_test_system.entity.Sample;
import com.adntest.adn_test_system.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;

    public Page<SampleResponse> getAllSamples(Pageable pageable) {
        return sampleRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public SampleResponse getSampleById(String id) {
        Sample sample = sampleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sample not found"));
        return mapToResponse(sample);
    }

    public SampleResponse createSample(SampleRequest request) {
        Sample sample = Sample.builder()
                .sampleType(request.getSampleType())
                .build();

        return mapToResponse(sampleRepository.save(sample));
    }

    public SampleResponse updateSample(String id, SampleRequest request) {
        Sample sample = sampleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sample not found"));

        sample.setSampleType(request.getSampleType());

        return mapToResponse(sampleRepository.save(sample));
    }

    public void deleteSample(String id) {
        Sample sample = sampleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sample not found"));
        sampleRepository.delete(sample);
    }

    public Page<SampleResponse> getSampleTypes(Pageable pageable) {
        return sampleRepository.findDistinctSampleTypes(pageable)
                .map(type -> SampleResponse.builder()
                        .sampleId(type)
                        .sampleType(type)
                        .build());
    }

    public Page<SampleResponse> getSamplesByType(String sampleType, Pageable pageable) {
        return sampleRepository.findBySampleType(sampleType, pageable)
                .map(this::mapToResponse);
    }

    private SampleResponse mapToResponse(Sample sample) {
        return SampleResponse.builder()
                .sampleId(sample.getSampleId())
                .sampleType(sample.getSampleType())
                .build();
    }
}
