package com.example.app.dto;

public record VisitDTOwithDTOs(
        Long id,
        TouristDTO tourist,
        CountryDTO country,
        int moneySpent,
        int daysSpent) {
}
