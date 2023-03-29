package com.example.app.model;

public record VisitDTOwithDTOs(Long id, TouristDTO tourist, CountryDTO country, int moneySpent, int daysSpent) {
}
