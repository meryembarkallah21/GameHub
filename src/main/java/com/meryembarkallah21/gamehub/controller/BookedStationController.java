package com.meryembarkallah21.gamehub.controller;
import com.meryembarkallah21.gamehub.service.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;



@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173") // Replace with your frontend's URL
public class BookedStationController {

    private final IBookingService bookingService;
}
