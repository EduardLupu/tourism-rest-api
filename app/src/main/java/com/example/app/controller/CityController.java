package com.example.app.controller;

import com.example.app.dto.CityDTOCountryDTO;
import com.example.app.dto.CityDTOCountryId;
import com.example.app.model.City;
import com.example.app.service.CityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }


    @GetMapping("/cities")
    public ResponseEntity<List<CityDTOCountryId>> getCities() {
        List<CityDTOCountryId> result = cityService.getCities();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<CityDTOCountryDTO> getCityById(@PathVariable Long id) {
        CityDTOCountryDTO city = cityService.getCityDTOCountryDTO(id);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @PostMapping("/cities")
    public ResponseEntity<City> create(@Valid @RequestBody City city) {
        return new ResponseEntity<>(cityService.createCity(city), HttpStatus.CREATED);
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<City> update(@PathVariable Long id, @Valid @RequestBody City city) {
        City cityToBeUpdated = cityService.updateCity(id, city);
        return new ResponseEntity<>(cityToBeUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        cityService.deleteCity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
