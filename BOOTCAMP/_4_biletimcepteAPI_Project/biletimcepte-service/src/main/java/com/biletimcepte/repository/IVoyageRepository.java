package com.biletimcepte.repository;

import com.biletimcepte.model.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IVoyageRepository extends JpaRepository<Voyage, Integer> {
    @Query(nativeQuery = true, value = "select *\n" +
                                        "from voyages\n" +
                                        "where available_seats > :available_seats\n" +
                                        "\tand (voyage_date_time >= :voyage_date_time and voyage_date_time <= :voyage_date_time2)\n" +
                                        "\tand voyage_status = upper(:voyage_status)")

    List<Voyage> selectVoyagesByParameters(int available_seats,
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") LocalDateTime voyage_date_time,
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") LocalDateTime voyage_date_time2,
                                             String voyage_status);

    @Query(nativeQuery = true, value = "select *\n" +
                                        "from voyages\n" +
                                        "where from_city = :from_city\n" +
                                        "\tand to_city = :to_city\n" +
                                        "\tand voyage_date_time = :voyage_date_time\n" +
                                        "\tand travel_type = :travel_type")
    Optional<Voyage> findVoyages(String from_city, String to_city,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime voyage_date_time,
                                 String travel_type);
}
