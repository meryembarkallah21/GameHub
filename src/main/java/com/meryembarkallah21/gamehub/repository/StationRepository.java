package com.meryembarkallah21.gamehub.repository;

import com.meryembarkallah21.gamehub.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long> {

    @Query("SELECT DISTINCT r.stationType FROM Station r")
    List<String> findDistinctStationTypes();

    @Query(" SELECT r FROM Station r " +
            " WHERE r.stationType LIKE %:stationType% " +
            " AND r.id NOT IN (" +
            "  SELECT br.station.id FROM BookedStation br " +
            "  WHERE ((br.checkInDate <= :checkOutDate) AND (br.checkOutDate >= :checkInDate))" +
            ")")

    List<Station> findAvailableStationsByDatesAndType(LocalDate checkInDate, LocalDate checkOutDate, String stationType);

}
