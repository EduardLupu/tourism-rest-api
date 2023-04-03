package com.example.app.dto;


public record CityDTOCountryDTO(
        Long cityId,
        String cityName,
        int citySurface,
        int cityPopulation,
        int cityPostalCode,
        CountryDTO country) {
}
