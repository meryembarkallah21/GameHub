package com.meryembarkallah21.gamehub.service;




import com.meryembarkallah21.gamehub.model.BookedStation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    @Override
    public List<BookedStation> getAllBookingsByStationId(Long stationId) {

        return null;
    }
}
