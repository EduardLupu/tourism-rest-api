package com.example.app.model;

public record CityDTOCountryId(
        Long cityId,
        String cityName,
        int citySurface,
        int cityPopulation,
        int cityPostalCode,
        Long countryId) {
}