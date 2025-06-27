package com.adntest.adn_test_system.service;

import com.adntest.adn_test_system.dto.request.AppointmentRequest;
import com.adntest.adn_test_system.dto.response.AppointmentResponse;
import com.adntest.adn_test_system.entity.Account;
import com.adntest.adn_test_system.entity.Appointment;
import com.adntest.adn_test_system.entity.TestOrder;
import com.adntest.adn_test_system.exception.AuthException;
import com.adntest.adn_test_system.repository.AccountRepository;
import com.adntest.adn_test_system.repository.AppointmentRepository;
import com.adntest.adn_test_system.repository.TestOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final TestOrderRepository testOrderRepository;
    private final AccountRepository accountRepository;


    public AppointmentResponse getAppointmentById(String id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        return mapToResponse(appointment);
    }

    public Page<AppointmentResponse> getAllAppointments(Pageable pageable) {
        return appointmentRepository.findAll(pageable)
                .map(this::mapToResponse);
    }






    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) {

        TestOrder testOrder = testOrderRepository.findById(appointmentRequest.getTestOrderId())
                    .orElseThrow(() -> new AuthException("Test order not found"));


        Account account = accountRepository.findById(appointmentRequest.getAccountId())
                    .orElseThrow(() -> new AuthException("Account not found"));


        Appointment newAppointment = Appointment.builder()
                .testOrder(testOrder)
                .scheduleDate(appointmentRequest.getScheduleDate())
                .location(appointmentRequest.getLocation())
                .account(account) // assuming it's set in the request
                .build();

        Appointment savedAppointment = appointmentRepository.save(newAppointment);

        return mapToResponse(savedAppointment);
    }


    public AppointmentResponse updateAppointment(String id, AppointmentRequest request) {
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        TestOrder testOrder = null;
        if (request.getTestOrderId() != null) {
            testOrder = testOrderRepository.findById(request.getTestOrderId())
                    .orElseThrow(() -> new AuthException("Test order not found"));
        }

        Appointment updatedAppointment = Appointment.builder()
                .appointmentId(existingAppointment.getAppointmentId())
                .scheduleDate(request.getScheduleDate())
                .location(request.getLocation())
                .account(existingAppointment.getAccount())   // preserve the account
                .testOrder(testOrder)                        // possibly updated
                .build();

        Appointment saved = appointmentRepository.save(updatedAppointment);
        return mapToResponse(saved);
    }


    public void deleteAppointment(String id) {
        if (!appointmentRepository.existsById(id)) {
            throw new AuthException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }



    public Optional<AppointmentResponse> getAppointmentByTestOrderId(String testOrderId) {
        return appointmentRepository.findByTestOrderId(testOrderId)
                .map(this::mapToResponse);
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .appointmentId(appointment.getAppointmentId())
                .orderId(appointment.getTestOrder() != null ? appointment.getTestOrder().getTestOrderId() : null)
                .scheduleDate(appointment.getScheduleDate())
                .location(appointment.getLocation())
                .accountId(appointment.getAccount() != null ? appointment.getAccount().getUserId() : null)
                .build();
    }
}

