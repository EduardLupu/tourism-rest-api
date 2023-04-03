package com.example.app.service;

import com.example.app.dto.CountryDTO;
import com.example.app.dto.CountryStatisticsDTO;
import com.example.app.exceptions.ResourceNotFoundException;
import com.example.app.model.City;
import com.example.app.model.Country;
import com.example.app.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    private final CityService cityService;

    public CountryService(CountryRepository countryRepository, CityService cityService) {
        this.countryRepository = countryRepository;
        this.cityService = cityService;
    }

    public Country assignCitiesToCountry(Long id, List<Long> cities_id_list) {
        Country country = getCountryById(id);
        for (Long city_id : cities_id_list) {
            City city = cityService.getCityById(city_id);
            if (city != null) {
                country.addCity(city);
                cityService.createCity(city);
            }
        }
        return countryRepository.save(country);
    }

    public List<CountryDTO> getCountriesWithPopulationHigherThan(int population) {
        return countryRepository.findAll()
                .stream()
                .filter(country -> country.getCountryPopulation() >= population)
                .map(country -> new CountryDTO(
                        country.getCountryId(),
                        country.getCountryName(),
                        country.getCountryPopulation(),
                        country.getCountrySurface(),
                        country.getCountryAbbreviation()))
                .collect(Collectors.toList());
    }

    public List<CountryDTO> getCountries() {
        return countryRepository.findAll()
                .stream()
                .map(country -> new CountryDTO(
                        country.getCountryId(),
                        country.getCountryName(),
                        country.getCountryPopulation(),
                        country.getCountrySurface(),
                        country.getCountryAbbreviation()))
                .collect(Collectors.toList());
    }

    public Country getCountryById(Long id) {
        return countryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Country with id " + id + " not found!")
        );
    }

    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country updateCountry(Long id, Country country) {
        Country countryToBeUpdated = getCountryById(id);
        countryToBeUpdated.setCountryName(country.getCountryName());
        countryToBeUpdated.setCountrySurface(country.getCountrySurface());
        countryToBeUpdated.setCountryPopulation(country.getCountryPopulation());
        countryToBeUpdated.setCountryAbbreviation(country.getCountryAbbreviation());
        return countryRepository.save(countryToBeUpdated);
    }

    public Country addCity(Long id, City city) {
        Country countryToBeUpdated = getCountryById(id);
        countryToBeUpdated.addCity(city);
        return countryRepository.save(countryToBeUpdated);
    }

    public void deleteCountry(Long id) {
        Country country = getCountryById(id);
        countryRepository.delete(country);
    }

    public List<CountryStatisticsDTO> getCountriesAverageDaysSpent() {
        return countryRepository.orderCountriesByAverageDaysSpent();
    }

    public Country assignCityToId(Long id, Long cityId) {
        City city = cityService.getCityById(cityId);
        return addCity(id, city);
    }
}
