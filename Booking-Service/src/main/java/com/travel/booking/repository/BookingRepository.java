package com.travel.booking.repository;

import com.travel.booking.entity.Booking;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	// Find all bookings made by a particular customer
    List<Booking> findByCustomerUsername(String customerUsername);

    // Find a particular booking belonging to a customer
    Optional<Booking> findByBookingIdAndCustomerUsername(
            Long bookingId, String customerUsername);

    // Find all bookings associated with a particular package
    List<Booking> findByPackageId(Long packageId);
}