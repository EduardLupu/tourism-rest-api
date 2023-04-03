package com.example.app.dto;

public record CountryDTO(
        Long countryId,
        String countryName,
        int countryPopulation,
        int countrySurface,
        String countryAbbreviation) {
}