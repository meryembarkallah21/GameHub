package com.meryembarkallah21.gamehub.service;

import com.meryembarkallah21.gamehub.model.BookedStation;

import java.util.List;

public interface IBookingService {

    List<BookedStation> getAllBookingsByStationId(Long stationId);
    String saveBooking(Long stationId, BookedStation bookingRequest);

    BookedStation findByBookingConfirmationCode(String confirmationCode);

    List<BookedStation> getAllBookings();

    void cancelBooking(Long bookingId);
    List<BookedStation> getBookingsByUserEmail(String email);


}
