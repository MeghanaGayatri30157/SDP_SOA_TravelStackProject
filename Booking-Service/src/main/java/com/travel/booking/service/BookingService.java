package com.travel.booking.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import com.travel.booking.service.PackageClient;
import org.springframework.stereotype.Service;

import com.travel.booking.entity.Booking;
import com.travel.booking.repository.BookingRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PackageClient packageClient;
    public BookingService(
            BookingRepository bookingRepository,
            PackageClient packageClient) {

        this.bookingRepository = bookingRepository;
        this.packageClient = packageClient;
    }

    public Booking createBooking(Booking booking, String username) {
        booking.setBookingId(null);
        booking.setCustomerUsername(username);
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getCustomerBookings(String username) {
        return bookingRepository.findByCustomerUsername(username);
    }

    public Optional<Booking> getCustomerBookingById(Long id, String username) {
        return bookingRepository.findByBookingIdAndCustomerUsername(id, username);
    }

    public List<Booking> getBookingsByPackageId(Long packageId) {
        return bookingRepository.findByPackageId(packageId);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }
    
    public List<Booking> getCompanyBookings(String token) {

        List<Long> packageIds =
                packageClient.getPackageIdsOwnedBy(token);

        List<Booking> companyBookings = new ArrayList<>();

        for (Long packageId : packageIds) {
            companyBookings.addAll(
                    bookingRepository.findByPackageId(packageId)
            );
        }

        return companyBookings;
    }
}