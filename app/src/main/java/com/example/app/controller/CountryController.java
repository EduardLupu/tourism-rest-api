package com.example.app.controller;


import com.example.app.dto.CountryDTO;
import com.example.app.dto.CountryStatisticsDTO;
import com.example.app.model.City;
import com.example.app.model.Country;
import com.example.app.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(value = "/countries", params = "population")
    public ResponseEntity<List<CountryDTO>> getCountries(@RequestParam(required = false) int population) {
        List<CountryDTO> countries = countryService.getCountriesWithPopulationHigherThan(population);
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @PostMapping("/countries/{id}/cities")
    public ResponseEntity<Country> assignCitiesToCountry(@PathVariable Long id, @RequestBody List<Long> cities_id_list) {
        Country country = countryService.assignCitiesToCountry(id, cities_id_list);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @GetMapping(value = "/countries/order-by-days-spent")
    public ResponseEntity<List<CountryStatisticsDTO>> getCountriesAverageDaysSpent() {
        List<CountryStatisticsDTO> countries = countryService.getCountriesAverageDaysSpent();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping(value = "/countries")
    public ResponseEntity<List<CountryDTO>> getCountries() {
        List<CountryDTO> countries = countryService.getCountries();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        Country country = countryService.getCountryById(id);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @PostMapping("/countries")
    public ResponseEntity<Country> create(@Valid @RequestBody Country country) {
        return new ResponseEntity<>(countryService.createCountry(country), HttpStatus.CREATED);
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<Country> update(@PathVariable Long id, @Valid @RequestBody Country country) {
        Country countryToBeUpdated = countryService.updateCountry(id, country);
        return new ResponseEntity<>(countryToBeUpdated, HttpStatus.OK);
    }

    @PostMapping("/countries/{id}/city")
    public ResponseEntity<Country> addCity(@PathVariable Long id, @Valid @RequestBody City city) {
        Country countryToBeUpdated = countryService.addCity(id, city);
        return new ResponseEntity<>(countryToBeUpdated, HttpStatus.OK);
    }

    @PostMapping("/countries/{id}/city/{cityId}")
    public ResponseEntity<Country> assignCityWithId(@PathVariable Long id, @PathVariable Long cityId) {
        Country country = countryService.assignCityToId(id, cityId);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
