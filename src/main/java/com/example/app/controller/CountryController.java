package com.example.app.controller;

import com.example.app.exceptions.NotFoundException;
import com.example.app.model.City;
import com.example.app.model.Country;
import com.example.app.repository.CountryRepository;
import com.example.app.service.CityService;
import com.example.app.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getCountries() {
        List <Country> countries = countryService.getCountries();
        if (countries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        Country country = countryService.getCountryById(id);
        if (country == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @GetMapping("/countries/{id}/cities")
    public ResponseEntity<List<City>> getCountryCities(@PathVariable Long id) {
        Country country = countryService.getCountryById(id);
        if (country == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(country.getCities(), HttpStatus.OK);
    }

    @PostMapping("/countries")
    public ResponseEntity<Country> create(@RequestBody Country country) {
        return new ResponseEntity<>(countryService.createCountry(country), HttpStatus.CREATED);
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<Country> update(@PathVariable Long id, @RequestBody Country country) {
        Country countryToBeUpdated = countryService.updateCountry(id, country);
        if (countryToBeUpdated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(countryToBeUpdated, HttpStatus.OK);
    }

    @PutMapping("/countries/{id}/city")
    public ResponseEntity<Country> addCity(@PathVariable Long id, @RequestBody City city) {
        Country countryToBeUpdated = countryService.addCity(id, city);
        if (countryToBeUpdated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(countryToBeUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        if (countryService.deleteCountry(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
