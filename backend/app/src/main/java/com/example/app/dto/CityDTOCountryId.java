package com.example.app.dto;

public record CityDTOCountryId(
        Long cityId,
        String cityName,
        int citySurface,
        int cityPopulation,
        int cityPostalCode,
        Long countryId) {
}