package com.travel.payment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.travel.payment.entity.Payment;
import com.travel.payment.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(Payment payment, String username) {
        payment.setPaymentId(null);
        payment.setCustomerUsername(username);
        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getCustomerPayments(String username) {
        return paymentRepository.findByCustomerUsername(username);
    }

    public Optional<Payment> getCustomerPaymentById(
            Long id, String username) {

        return paymentRepository
                .findByPaymentIdAndCustomerUsername(id, username);
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }
}