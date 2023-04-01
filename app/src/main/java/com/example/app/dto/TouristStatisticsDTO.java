package com.example.app.dto;

import java.time.LocalDate;

public record TouristStatisticsDTO(
        Long id,
        String name,
        LocalDate touristDateOfBirth,
        String touristGender,
        Long totalMoneySpentInVisits) {
}
