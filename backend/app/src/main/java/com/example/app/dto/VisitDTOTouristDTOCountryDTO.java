package com.example.app.dto;

public record VisitDTOTouristDTOCountryDTO(
        Long id,
        TouristDTO tourist,
        CountryDTO country,
        int moneySpent,
        int daysSpent) {
}
