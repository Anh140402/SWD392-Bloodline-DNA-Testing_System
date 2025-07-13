package com.adntest.adn_test_system.service;

import com.adntest.adn_test_system.dto.request.SampleRequest;
import com.adntest.adn_test_system.dto.response.SampleResponse;
import com.adntest.adn_test_system.entity.Sample;
import com.adntest.adn_test_system.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;

    public List<SampleResponse> getAllSamples() {
        return sampleRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
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

    public List<SampleResponse> getSampleTypes() {
        return sampleRepository.findDistinctSampleTypes()
            .stream()
            .map(type -> SampleResponse.builder().sampleId(type).sampleType(type).build())
            .collect(Collectors.toList());
    }

    public List<SampleResponse> getSamplesByType(String sampleType) {
        return sampleRepository.findBySampleType(sampleType)
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    private SampleResponse mapToResponse(Sample sample) {
        return SampleResponse.builder()
                .sampleId(sample.getSampleId())
                .sampleType(sample.getSampleType())
                .build();
    }
}
