package com.meryembarkallah21.gamehub.controller;

import com.meryembarkallah21.gamehub.model.Station;
import com.meryembarkallah21.gamehub.response.StationResponse;
import com.meryembarkallah21.gamehub.service.IStationService;
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


@RestController
@RequiredArgsConstructor
@RequestMapping("/stations")
public class StationController {


    private final IStationService stationService;



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


}
