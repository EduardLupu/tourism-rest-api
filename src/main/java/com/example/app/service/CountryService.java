package com.example.app.service;

import com.example.app.model.City;
import com.example.app.model.Country;
import com.example.app.repository.CityRepository;
import com.example.app.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    public List<Country> getCountriesWithPopulationHigherThan(int population) {
        return countryRepository.findAll()
                .stream()
                .filter(country -> country.getCountryPopulation() >= population)
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

}
