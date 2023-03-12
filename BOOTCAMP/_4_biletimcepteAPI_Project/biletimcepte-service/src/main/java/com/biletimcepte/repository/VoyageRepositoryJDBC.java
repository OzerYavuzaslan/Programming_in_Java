package com.biletimcepte.repository;

import com.biletimcepte.model.Voyage;
import com.biletimcepte.model.enums.VoyageStatus;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Repository
public class VoyageRepositoryJDBC implements IVoyageRepositoryJDBC{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public VoyageRepositoryJDBC (NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Voyage> filterVoyages(int availableSeats, LocalDateTime startDateTime, LocalDateTime endDateTime, String travelType, String fromCity, String toCity) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        String voyagesSQL = "select * from voyages where available_seats > :available_seats ";
        mapSqlParameterSource.addValue("available_seats", availableSeats);

        if (startDateTime != null || endDateTime != null) {
            voyagesSQL += "and (voyage_date_time >= :startdatetime and voyage_date_time <= :enddatetime) ";
            mapSqlParameterSource.addValue("startdatetime", startDateTime)
                                 .addValue("enddatetime", endDateTime);
        }

        if (travelType != null) {
            voyagesSQL += "and travel_type = upper(:travel_type) ";
            mapSqlParameterSource.addValue("travel_type", travelType);
        }

        if (fromCity != null) {
            voyagesSQL += "and upper(from_city) = upper(:from_city) ";
            mapSqlParameterSource.addValue("from_city", fromCity);
        }

        if (toCity != null) {
            voyagesSQL += "and upper(to_city) = upper(:to_city) ";
            mapSqlParameterSource.addValue("to_city", toCity);
        }

        voyagesSQL += "and voyage_status = upper(:voyage_status)";
        mapSqlParameterSource.addValue("voyage_status", VoyageStatus.ACTIVE.toString());

        return getJdbcTemplate().query(voyagesSQL, mapSqlParameterSource, new BeanPropertyRowMapper <> (Voyage.class));
    }
}
