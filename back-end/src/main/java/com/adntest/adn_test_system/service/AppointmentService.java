package com.adntest.adn_test_system.service;

import com.adntest.adn_test_system.dto.request.AppointmentRequest;
import com.adntest.adn_test_system.dto.response.AppointmentResponse;
import com.adntest.adn_test_system.entity.Account;
import com.adntest.adn_test_system.entity.Appointment;
import com.adntest.adn_test_system.exception.AuthException;
import com.adntest.adn_test_system.repository.AccountRepository;
import com.adntest.adn_test_system.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AccountRepository accountRepository;

    public AppointmentResponse getAppointmentById(String id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AuthException("Appointment not found"));
        return mapToResponse(appointment);
    }

    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) {
        Account account = accountRepository.findById(appointmentRequest.getAccountId())
                .orElseThrow(() -> new AuthException("Account not found"));

        Appointment newAppointment = Appointment.builder()
                .scheduleDate(appointmentRequest.getScheduleDate())
                .location(appointmentRequest.getLocation())
                .account(account)
                .build();

        return mapToResponse(appointmentRepository.save(newAppointment));
    }

    public AppointmentResponse updateAppointment(String id, AppointmentRequest request) {
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AuthException("Appointment not found"));

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new AuthException("Account not found"));

        Appointment updatedAppointment = Appointment.builder()
                .appointmentId(existingAppointment.getAppointmentId())
                .scheduleDate(request.getScheduleDate())
                .location(request.getLocation())
                .account(account)
                .build();

        return mapToResponse(appointmentRepository.save(updatedAppointment));
    }

    public void deleteAppointment(String id) {
        if (!appointmentRepository.existsById(id)) {
            throw new AuthException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .appointmentId(appointment.getAppointmentId())
                .scheduleDate(appointment.getScheduleDate())
                .location(appointment.getLocation())
                .accountId(appointment.getAccount() != null ? appointment.getAccount().getUserId() : null)
                .build();
    }
}