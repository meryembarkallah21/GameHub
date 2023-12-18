package com.meryembarkallah21.gamehub.controller;

import com.meryembarkallah21.gamehub.exception.InvalidBookingRequestException;
import com.meryembarkallah21.gamehub.exception.ResourceNotFoundException;
import com.meryembarkallah21.gamehub.model.BookedStation;
import com.meryembarkallah21.gamehub.model.Station;
import com.meryembarkallah21.gamehub.response.BookingResponse;
import com.meryembarkallah21.gamehub.response.StationResponse;
import com.meryembarkallah21.gamehub.service.IBookingService;
import com.meryembarkallah21.gamehub.service.IStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final IBookingService bookingService;
    private final IStationService stationService;

    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookedStation> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedStation booking : bookings){
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @PostMapping("/station/{stationId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long stationId,
                                         @RequestBody BookedStation bookingRequest){
        try{
            String confirmationCode = bookingService.saveBooking(stationId, bookingRequest);
            return ResponseEntity.ok(
                    "Station booked successfully, Your booking confirmation code is :"+confirmationCode);

        }catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

   @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try{
            BookedStation booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


     /*
    @GetMapping("/user/{email}/bookings")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserEmail(@PathVariable String email) {
        List<BookedStation> bookings = bookingService.getBookingsByUserEmail(email);
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedStation booking : bookings) {
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }*/

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
    }

    private BookingResponse getBookingResponse(BookedStation booking) {
        Station theStation = stationService.getStationById(booking.getStation().getId()).get();
        StationResponse station = new StationResponse(
                theStation.getId(),
                theStation.getStationType(),
                theStation.getStationPrice());
        return new BookingResponse(
                booking.getBookingId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getGuestFullName(),
                booking.getGuestEmail(),
                booking.getNumOfPcGamers(),
                booking.getNumOfConsoleGamers(),
                booking.getTotalNumOfGuest(),
                booking.getBookingConfirmationCode(),
                station);
    }
}