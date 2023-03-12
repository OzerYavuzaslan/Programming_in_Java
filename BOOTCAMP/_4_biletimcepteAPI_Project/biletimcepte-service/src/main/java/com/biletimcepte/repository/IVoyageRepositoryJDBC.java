package com.biletimcepte.repository;

import com.biletimcepte.model.Voyage;

import java.time.LocalDateTime;
import java.util.List;

public interface IVoyageRepositoryJDBC {
    List<Voyage> filterVoyages(int availableSeats, LocalDateTime startDateTime, LocalDateTime endDateTime, String travelType, String fromCity, String toCity);
}
