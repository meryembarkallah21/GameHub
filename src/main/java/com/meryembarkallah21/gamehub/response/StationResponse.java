package com.meryembarkallah21.gamehub.response;



import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class StationResponse {

    private Long id;
    private String stationType;
    private BigDecimal stationPrice;
    private boolean isBooked;
    private String photo;
    private List<BookingResponse>bookings;

    public StationResponse(Long id, String stationType, BigDecimal stationPrice) {
        this.id = id;
        this.stationType = stationType;
        this.stationPrice = stationPrice;
    }

    public StationResponse(Long id, String stationType, BigDecimal stationPrice, boolean isBooked,
                        byte[] photoBytes) {
        this.id = id;
        this.stationType = stationType;
        this.stationPrice = stationPrice;
        this.isBooked = isBooked;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;this.bookings = bookings;
    }
}
