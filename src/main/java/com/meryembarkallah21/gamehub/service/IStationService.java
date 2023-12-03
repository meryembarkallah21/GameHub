package com.meryembarkallah21.gamehub.service;


import com.meryembarkallah21.gamehub.exception.ResourceNotFoundException;
import com.meryembarkallah21.gamehub.model.Station;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IStationService {
    Station addNewStation(MultipartFile photo, String stationType, BigDecimal stationPrice) throws SQLException, IOException;

    List<String> getAllStationTypes();

    List<Station> getAllStations();

    byte[] getStationPhotoByStationId(Long stationId) throws SQLException, ResourceNotFoundException;
}
