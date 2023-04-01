package com.example.app.service;

import com.example.app.dto.CountryDTO;
import com.example.app.dto.CountryStatisticsDTO;
import com.example.app.model.City;
import com.example.app.model.Country;
import com.example.app.repository.CityRepository;
import com.example.app.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    private final CityRepository cityRepository;

    public CountryService(CountryRepository countryRepository, CityRepository cityRepository) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    public void assignCitiesToCountry(Long id, List<Long> cities_id_list) {
        Country country = getCountryById(id);
        for (Long city_id : cities_id_list) {
            if (cityRepository.existsById(city_id)) {
                City city = cityRepository.getReferenceById(city_id);
                country.addCity(city);
                cityRepository.save(city);
            }
        }
        countryRepository.save(country);
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

    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryById(Long id) {
        return countryRepository.findById(id).orElse(null);
    }

    public Country createCountry(Country country) {
        Country countrySaved = countryRepository.save(country);
        cityRepository.saveAll(countrySaved.getCities());
        return countrySaved;
    }

    public Country updateCountry(Long id, Country country) {
        if (countryRepository.existsById(id)) {
            Country countryToBeUpdated = getCountryById(id);
            countryToBeUpdated.setCountryName(country.getCountryName());
            countryToBeUpdated.setCountrySurface(country.getCountrySurface());
            countryToBeUpdated.setCountryPopulation(country.getCountryPopulation());
            countryToBeUpdated.setCountryAbbreviation(country.getCountryAbbreviation());
            countryToBeUpdated.setCities(country.getCities());
            return countryRepository.save(countryToBeUpdated);
        }
        return null;
    }

    public Country addCity(Long id, City city) {
        if (countryRepository.existsById(id)) {
            Country countryToBeUpdated = getCountryById(id);
            countryToBeUpdated.addCity(city);
            return countryRepository.save(countryToBeUpdated);
        }
        return null;
    }

    public Country removeCity(Long id, City city) {
        if (countryRepository.existsById(id)) {
            Country countryToBeUpdated = getCountryById(id);
            countryToBeUpdated.removeCity(city);
            return countryRepository.save(countryToBeUpdated);
        }
        return null;
    }


    public boolean deleteCountry(Long id) {
        if (countryRepository.existsById(id)) {
            countryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<CountryStatisticsDTO> getCountriesAverageDaysSpent() {
        return countryRepository.orderCountriesByAverageDaysSpent();
    }
}
