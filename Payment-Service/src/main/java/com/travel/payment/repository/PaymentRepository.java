package com.travel.payment.repository;


import com.travel.payment.entity.Payment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	List<Payment> findByCustomerUsername(String customerUsername);

	Optional<Payment> findByPaymentIdAndCustomerUsername(
	        Long paymentId, String customerUsername);
}