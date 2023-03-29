package com.example.app.controller;


import com.example.app.model.City;
import com.example.app.model.Country;
import com.example.app.model.CountryStatisticsDTO;
import com.example.app.model.CountryDTO;
import com.example.app.service.CityService;
import com.example.app.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @GetMapping(value = "/countries", params = "population")
    public ResponseEntity<List<CountryDTO>> getCountries(@RequestParam(required = false) int population) {
        List <Country> countries = countryService.getCountriesWithPopulationHigherThan(population);
        List<CountryDTO> hCountries = new ArrayList<>();
        for (Country c: countries)
        {
            hCountries.add(new CountryDTO(c.getCountryId(), c.getCountryName(), c.getCountrySurface(), c.getCountryPopulation(), c.getCountryAbbreviation()));
        }
        if (hCountries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(hCountries, HttpStatus.OK);
    }

    @PostMapping("/countries/{id}/cities")
    public ResponseEntity<HttpStatus> assignCitiesToCountry(@PathVariable Long id, @RequestBody List<Long> cities_id_list) {
        countryService.assignCitiesToCountry(id, cities_id_list);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/countries/stats")
    public ResponseEntity<List<CountryStatisticsDTO>> getCountriesAverageMoneySpent() {
        List <CountryStatisticsDTO> countries = countryService.getCountriesAverageDaysSpent();
        if (countries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping(value = "/countries")
    public ResponseEntity<List<CountryDTO>> getCountries() {
        List <Country> countries = countryService.getCountries();
        List<CountryDTO> countriesDTO = new ArrayList<>();
        for (Country c: countries)
        {
            countriesDTO.add(new CountryDTO(c.getCountryId(), c.getCountryName(), c.getCountrySurface(), c.getCountryPopulation(), c.getCountryAbbreviation()));
        }
        if (countriesDTO.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(countriesDTO, HttpStatus.OK);
    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        Country country = countryService.getCountryById(id);
        if (country == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @PostMapping("/countries")
    public ResponseEntity<Country> create(@Valid @RequestBody Country country) {
        return new ResponseEntity<>(countryService.createCountry(country), HttpStatus.CREATED);
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<Country> update(@PathVariable Long id, @Valid @RequestBody Country country) {
        Country countryToBeUpdated = countryService.updateCountry(id, country);
        if (countryToBeUpdated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(countryToBeUpdated, HttpStatus.OK);
    }

    @PostMapping("/countries/{id}/city")
    public ResponseEntity<Country> addCity(@PathVariable Long id, @Valid @RequestBody City city) {
        Country countryToBeUpdated = countryService.addCity(id, city);
        if (countryToBeUpdated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(countryToBeUpdated, HttpStatus.OK);
    }

    @PostMapping("/countries/{id}/city/{cityId}")
    public ResponseEntity<Country> addCityWithId(@PathVariable Long id, @PathVariable Long cityId) {
        City city = cityService.getCityById(cityId);
        if (city == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
