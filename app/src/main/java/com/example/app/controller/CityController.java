package com.example.app.controller;

import com.example.app.model.*;
import com.example.app.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/cities")
    public ResponseEntity<List<CityDTOCountryId>> getCities() {
        List<City> cities = cityService.getCities();
        List<CityDTOCountryId> hCities = new ArrayList<>();
        for (City c: cities){
            hCities.add(new CityDTOCountryId(c.getCityId(), c.getCityName(), c.getCitySurface(), c.getCityPopulation(), c.getCityPopulation(),
                    (c.getCountry() == null) ? null : c.getCountry().getCountryId()));
        }
        if (hCities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hCities, HttpStatus.OK);
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<CityDTOCountryDTO> getCityById(@PathVariable Long id) {
        City city = cityService.getCityById(id);
        if (city == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Country c = city.getCountry();
        CountryDTO hCountry = null;
        if (c != null)
            hCountry = new CountryDTO(c.getCountryId(), c.getCountryName(), c.getCountrySurface(), c.getCountrySurface(), c.getCountryAbbreviation());
        CityDTOCountryDTO last_city = new CityDTOCountryDTO(city.getCityId(),
                city.getCityName(),
                city.getCitySurface(),
                city.getCityPopulation(),
                city.getCityPostalCode(),
                hCountry);
        return new ResponseEntity<>(last_city, HttpStatus.OK);
    }

    @PostMapping("/cities")
    public ResponseEntity<City> create(@RequestBody City city) {
        return new ResponseEntity<>(cityService.createCity(city), HttpStatus.CREATED);
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<City> update(@PathVariable Long id, @RequestBody City city) {
        City cityToBeUpdated = cityService.updateCity(id, city);
        if (cityToBeUpdated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cityToBeUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        if (cityService.deleteCity(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
