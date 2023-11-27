package com.meryembarkallah21.gamehub.service;



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
        if (!file.isEmpty()){
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            station.setPhoto(photoBlob);
        }
        return stationRepository.save(station);
    }



}
