package com.meryembarkallah21.gamehub.service;

import com.meryembarkallah21.gamehub.model.BookedStation;

import java.util.List;

public interface IBookingService {

    List<BookedStation> getAllBookingsByStationId(Long stationId);


}
