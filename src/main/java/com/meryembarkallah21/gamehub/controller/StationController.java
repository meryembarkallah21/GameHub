package com.meryembarkallah21.gamehub.controller;

import com.meryembarkallah21.gamehub.exception.PhotoRetrievalException;
import com.meryembarkallah21.gamehub.exception.ResourceNotFoundException;
import com.meryembarkallah21.gamehub.model.BookedStation;
import com.meryembarkallah21.gamehub.model.Station;
import com.meryembarkallah21.gamehub.response.BookingResponse;
import com.meryembarkallah21.gamehub.response.StationResponse;
import com.meryembarkallah21.gamehub.service.BookingService;
import com.meryembarkallah21.gamehub.service.IStationService;
import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;



@RestController
@RequiredArgsConstructor
@RequestMapping("/stations")
@CrossOrigin(origins = "http://localhost:5173") // Replace with your frontend's URL

public class StationController {


    private final IStationService stationService;
    private final BookingService bookingService;



    //n7otou http method
    @PostMapping("/add/new-station")
    public ResponseEntity<StationResponse> addNewStation(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("stationType") String stationType,
            @RequestParam("stationPrice") BigDecimal stationPrice) throws SQLException, IOException {
        Station savedStation = stationService.addNewStation(photo, stationType, stationPrice);
        StationResponse response = new StationResponse(savedStation.getId(), savedStation.getStationType(),
                savedStation.getStationPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/station/types")
    public List<String> getStationTypes() {

        return stationService.getAllStationTypes();
    }

    @GetMapping("/all-stations")
    public ResponseEntity<List<StationResponse>> getAllStations() throws SQLException {
        List<Station> stations = stationService.getAllStations();
        List<StationResponse> stationResponses = new ArrayList<>();
        for (Station station : stations) {
            byte[] photoBytes = stationService.getStationPhotoByStationId(station.getId());
            if (photoBytes != null && photoBytes.length > 0) {
                String base64Photo = Base64.encodeBase64String(photoBytes);
                StationResponse stationResponse = getStationResponse(station);
                stationResponse.setPhoto(base64Photo);
                stationResponses.add(stationResponse);
            }
        }
        return ResponseEntity.ok(stationResponses);
    }





    @DeleteMapping("/delete/station/{stationId}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long stationId){
        stationService.deleteStation(stationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @PutMapping("/update/{stationId}")
    public ResponseEntity<StationResponse> updateStation(@PathVariable Long stationId,
                                                   @RequestParam(required = false)  String stationType,
                                                   @RequestParam(required = false) BigDecimal stationPrice,
                                                   @RequestParam(required = false) MultipartFile photo) throws SQLException, IOException {
        byte[] photoBytes = photo != null && !photo.isEmpty() ?
                photo.getBytes() : stationService.getStationPhotoByStationId(stationId);
        Blob photoBlob = photoBytes != null && photoBytes.length >0 ? new SerialBlob(photoBytes): null;
        Station theStation = stationService.updateStation(stationId, stationType, stationPrice, photoBytes);
        theStation.setPhoto(photoBlob);
        StationResponse stationResponse = getStationResponse(theStation);
        return ResponseEntity.ok(stationResponse);
    }

    @GetMapping("/station/{stationId}")
    public ResponseEntity<Optional<StationResponse>> getStationById(@PathVariable Long stationId){
        Optional<Station> theStation = stationService.getStationById(stationId);
        return theStation.map(station -> {
            StationResponse stationResponse = getStationResponse(station);
            return  ResponseEntity.ok(Optional.of(stationResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Station not found"));
    }


    private StationResponse getStationResponse(Station station) {
        List<BookedStation> bookings = getAllBookingsByStationId(station.getId());
        /*List<BookingResponse> bookingInfo = bookings
                .stream()
                .map(booking -> new BookingResponse(booking.getBookingId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(), booking.getBookingConfirmationCode())).toList();*/
        byte[] photoBytes = null;
        Blob photoBlob = station.getPhoto();
        if (photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrievalException("Error retrieving photo");
            }
        }
        return new StationResponse(station.getId(),
                station.getStationType(), station.getStationPrice(),
                station.isBooked(), photoBytes);
    }

    private List<BookedStation> getAllBookingsByStationId(Long stationId) {
        return bookingService.getAllBookingsByStationId(stationId);

    }

}
