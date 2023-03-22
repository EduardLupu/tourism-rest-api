package com.example.app.model;

import java.time.LocalDate;

public record TouristStatisticsDTO(Long id, String name, LocalDate touristDateOfBirth, String touristGender, Long totalMoneySpentInVisits) {
}
