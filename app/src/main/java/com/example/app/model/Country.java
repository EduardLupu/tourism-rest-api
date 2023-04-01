package com.example.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    @NotBlank(message = "Error: countryName shouldn't be blank!")
    private String countryName;
    @Min(value = 0, message = "Error: countrySurface shouldn't be negative!")
    private int countrySurface;
    @Min(value = 0, message = "Error: countryPopulation shouldn't be negative!")
    private int countryPopulation;
    @NotBlank(message = "Error: countryAbbreviation shouldn't be blank!")
    private String countryAbbreviation;

    @OneToMany(
            mappedBy = "country",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<City> cities = new ArrayList<>();

    @OneToMany(mappedBy = "country")
    private List<Visit> visits = new ArrayList<>();

    public List<Visit> getVisits() {
        return visits;
    }

    public void addCity(City city) {
        cities.add(city);
        city.setCountry(this);
    }

    public void removeCity(City city) {
        cities.remove(city);
        city.setCountry(null);
    }

    public Country() {

    }

    public Long getCountryId() {
        return countryId;
    }

    public Country(String countryName, int countrySurface, int countryPopulation, String countryAbbreviation) {
        this.countryName = countryName;
        this.countrySurface = countrySurface;
        this.countryPopulation = countryPopulation;
        this.countryAbbreviation = countryAbbreviation;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCountrySurface() {
        return countrySurface;
    }

    public void setCountrySurface(int countrySurface) {
        this.countrySurface = countrySurface;
    }

    public int getCountryPopulation() {
        return countryPopulation;
    }

    public void setCountryPopulation(int countryPopulation) {
        this.countryPopulation = countryPopulation;
    }

    public String getCountryAbbreviation() {
        return countryAbbreviation;
    }

    public void setCountryAbbreviation(String countryAbbreviation) {
        this.countryAbbreviation = countryAbbreviation;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }
}
