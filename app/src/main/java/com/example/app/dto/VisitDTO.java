package com.example.app.dto;

public record VisitDTO(
        Long id,
        Long touristId,
        Long countryId,
        int moneySpent,
        int daysSpent) {
}
