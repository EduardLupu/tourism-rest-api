package com.example.app.model;

public record CountryDTO(
        Long countryId,
        String countryName,
        int countrySurface,
        int countryPopulation,
        String countryAbbreviation) {
}