package com.meryembarkallah21.gamehub.service;


import com.meryembarkallah21.gamehub.exception.InternalServerException;
import com.meryembarkallah21.gamehub.exception.ResourceNotFoundException;
import com.meryembarkallah21.gamehub.model.Station;
import com.meryembarkallah21.gamehub.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StationService implements IStationService

{
    private final StationRepository stationRepository;

    @Override
    public Station addNewStation(MultipartFile file, String stationType, BigDecimal stationPrice) throws SQLException, IOException {

        Station station = new Station();
        station.setStationType(stationType);
        station.setStationPrice(stationPrice);

        try {
            if (!file.isEmpty()) {
                byte[] photoBytes = file.getBytes();
                Blob photoBlob = new SerialBlob(photoBytes);
                station.setPhoto(photoBlob);
            }
            // Add logging to trace values and flow
            System.out.println("Station to be saved: " + station.toString());

            return stationRepository.save(station);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            throw e; // or handle the exception gracefully
        }
    }


    @Override
    public List<String> getAllStationTypes() {

        return stationRepository.findDistinctStationTypes();
    }

    @Override
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    @Override
    public byte[] getStationPhotoByStationId(Long stationId) throws SQLException, ResourceNotFoundException {

        Optional<Station> theStation = stationRepository.findById(stationId);

        if(theStation.isEmpty()){
            throw new ResourceNotFoundException("Sorry, Station not found!");
        }
        Blob photoBlob = theStation.get().getPhoto();
        if(photoBlob != null){
            return photoBlob.getBytes(1, (int) photoBlob.length());
        }
        return null;
    }


}
