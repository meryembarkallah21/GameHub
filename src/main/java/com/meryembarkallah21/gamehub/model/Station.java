package com.meryembarkallah21.gamehub.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String stationType;
    private BigDecimal stationPrice;
    private boolean isBooked = false;


    @Lob
    private Blob photo;

    @OneToMany(mappedBy="station", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookedStation> bookings;

    public Station() {this.bookings = new ArrayList<>();}

    public void addBooking(BookedStation booking){
        if (bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setStation(this);
        isBooked = true;
        String bookingCode = RandomStringUtils.randomNumeric(10); //10 hiya longeur mta3 code
        booking.setBookingConfirmationCode(bookingCode);
    }
}
