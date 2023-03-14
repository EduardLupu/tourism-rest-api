package com.example.app.service;

import com.example.app.model.City;
import com.example.app.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

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
            cityToBeUpdated.setCityName(city.getCityName());
            cityToBeUpdated.setCitySurface(city.getCitySurface());
            cityToBeUpdated.setCityPopulation(city.getCityPopulation());
            cityToBeUpdated.setCountry(city.getCountry());
            cityToBeUpdated.setCityPostalCode(city.getCityPostalCode());
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
}
