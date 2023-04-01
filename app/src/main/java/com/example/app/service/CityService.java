package com.example.app.service;

import com.example.app.dto.CityDTOCountryDTO;
import com.example.app.dto.CityDTOCountryId;
import com.example.app.dto.CountryDTO;
import com.example.app.exceptions.ResourceNotFoundException;
import com.example.app.model.City;
import com.example.app.model.Country;
import com.example.app.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("City with id " + id + " not found!")
        );
    }

    public City createCity(City city) {
        return cityRepository.save(city);
    }

    public City updateCity(Long id, City city) {
        City cityToBeUpdated = getCityById(id);
        Country countryToBeUpdated = cityToBeUpdated.getCountry();
        countryToBeUpdated.removeCity(cityToBeUpdated);
        cityToBeUpdated.setCityName(city.getCityName());
        cityToBeUpdated.setCitySurface(city.getCitySurface());
        cityToBeUpdated.setCityPopulation(city.getCityPopulation());
        cityToBeUpdated.setCityPostalCode(city.getCityPostalCode());
        countryToBeUpdated.addCity(cityToBeUpdated);
        return cityRepository.save(cityToBeUpdated);
    }

    public void deleteCity(Long id) {
        City city = getCityById(id);
        cityRepository.delete(city);
    }

    public CityDTOCountryDTO getCityDTOCountryDTO(Long id) {
        City city = getCityById(id);
        return new CityDTOCountryDTO(
                city.getCityId(),
                city.getCityName(),
                city.getCitySurface(),
                city.getCityPopulation(),
                city.getCityPopulation(),
                (city.getCountry() == null) ? null : new CountryDTO(
                        city.getCountry().getCountryId(),
                        city.getCountry().getCountryName(),
                        city.getCountry().getCountryPopulation(),
                        city.getCountry().getCountrySurface(),
                        city.getCountry().getCountryAbbreviation())
        );
    }

    public List<CityDTOCountryId> getCities() {
        return cityRepository.findAll()
                .stream()
                .map(c -> new CityDTOCountryId(
                        c.getCityId(),
                        c.getCityName(),
                        c.getCitySurface(),
                        c.getCityPopulation(),
                        c.getCityPopulation(),
                        (c.getCountry() == null) ? null : c.getCountry().getCountryId()))
                .collect(Collectors.toList());
    }
}
