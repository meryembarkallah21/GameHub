package com.meryembarkallah21.gamehub.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;



@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BookedStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long bookingId;


    @Column(name = "check_in") //to change column name in db
    private LocalDate checkInDate;

    @Column(name = "check_out")
    private LocalDate checkOutDate;

    @Column(name = "guest_fullName")
    private String guestFullName;

    @Column(name = "guest_email")
    private String guestEmail;

    @Column(name = "console-gamers")
    private int NumOfConsoleGamers;

    @Column(name = "pc_gamers")
    private int NumOfPcGamers;


    @Column(name = "total_guest")
    private int totalNumOfGuest;

    @Column(name = "confirmation_Code")
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;



    public void calculateTotalNumberOfGuest(){
        this.totalNumOfGuest = this.NumOfConsoleGamers + NumOfPcGamers;
    }

    public void setNumOfConsoleGamers(int numOfConsoleGamers) {
        NumOfConsoleGamers = numOfConsoleGamers;
        calculateTotalNumberOfGuest();
    }

    public void setNumOfPcGamers(int numOfPcGamers) {
        NumOfPcGamers = numOfPcGamers;
        calculateTotalNumberOfGuest();
    }
}
