package com.adntest.adn_test_system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adntest.adn_test_system.dto.request.ServiceRequest;
import com.adntest.adn_test_system.dto.response.ServiceResponse;
import com.adntest.adn_test_system.exception.AuthException;
import com.adntest.adn_test_system.repository.ServiceRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class    ServiceService {
    private final ServiceRepository serviceRepository;

    public List<ServiceResponse> getAllServices() {
        return serviceRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public ServiceResponse getServiceById(String id) {
        com.adntest.adn_test_system.entity.Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new AuthException("Service not found"));
        return mapToResponse(service);
    }

    public ServiceResponse createService(ServiceRequest request) {
        if (serviceRepository.existsByServiceName(request.getServiceName())) {
            throw new AuthException("Service with this name already exists");
        }

        com.adntest.adn_test_system.entity.Service service = mapToEntity(request);
        com.adntest.adn_test_system.entity.Service savedService = serviceRepository.save(service);
        return mapToResponse(savedService);
    }

    public ServiceResponse updateService(String id, ServiceRequest request) {
        com.adntest.adn_test_system.entity.Service existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new AuthException("Service not found"));

        if (!existingService.getServiceName().equals(request.getServiceName()) 
                && serviceRepository.existsByServiceName(request.getServiceName())) {
            throw new AuthException("Service with this name already exists");
        }

        existingService.setServiceName(request.getServiceName());
        existingService.setDescription(request.getDescription());
        existingService.setPrice(request.getPrice());
        existingService.setServiceType(request.getServiceType());

        com.adntest.adn_test_system.entity.Service updatedService = serviceRepository.save(existingService);
        return mapToResponse(updatedService);
    }

    public void deleteService(String id) {
        if (!serviceRepository.existsById(id)) {
            throw new AuthException("Service not found");
        }
        serviceRepository.deleteById(id);
    }

    public List<ServiceResponse> getServicesByType(String serviceType) {
        return serviceRepository.findByServiceType(serviceType)
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    private ServiceResponse mapToResponse(com.adntest.adn_test_system.entity.Service service) {
        ServiceResponse response = new ServiceResponse();
        response.setServiceId(service.getServiceId());
        response.setServiceName(service.getServiceName());
        response.setDescription(service.getDescription());
        response.setPrice(service.getPrice());
        response.setServiceType(service.getServiceType());
        return response;
    }

    private com.adntest.adn_test_system.entity.Service mapToEntity(ServiceRequest request) {
        return com.adntest.adn_test_system.entity.Service.builder()
                .serviceName(request.getServiceName())
                .description(request.getDescription())
                .price(request.getPrice())
                .serviceType(request.getServiceType())
                .build();
    }
}

