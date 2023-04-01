package com.example.app.service;

import com.example.app.dto.CityDTOCountryId;
import com.example.app.model.City;
import com.example.app.model.Country;
import com.example.app.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {


    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getCities() {
        return cityRepository.findAll();
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    public City createCity(City city) {
        return cityRepository.save(city);
    }

    public City updateCity(Long id, City city) {
        if (cityRepository.existsById(id)) {
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
        return null;
    }

    public boolean deleteCity(Long id) {
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<CityDTOCountryId> getCitiesDTOCountryId() {
        List<City> cities = cityRepository.findAll();
        List<CityDTOCountryId> result = new ArrayList<>();
        for (City c : cities) {
            result.add(
                    new CityDTOCountryId(
                            c.getCityId(),
                            c.getCityName(),
                            c.getCitySurface(),
                            c.getCityPopulation(),
                            c.getCityPopulation(),
                            (c.getCountry() == null) ? null : c.getCountry().getCountryId()
                    )
            );
        }
        return result;
    }
}
