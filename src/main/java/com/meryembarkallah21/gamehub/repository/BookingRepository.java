package com.meryembarkallah21.gamehub.repository;

import com.meryembarkallah21.gamehub.model.BookedStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;



public interface BookingRepository extends JpaRepository<BookedStation, Long> {

    List<BookedStation> findByStationId(Long stationId);

    Optional<BookedStation> findByBookingConfirmationCode(String confirmationCode);

    List<BookedStation> findByGuestEmail(String email);
}