package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;
    private String countryName;
    private int countrySurface;
    private int countryPopulation;
    private String countryAbbreviation;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "country",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<City> cities = new ArrayList<>();

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

    public void setCities(List<City> cities) {
        this.cities.clear();
        for (City city: cities) {
            addCity(city);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return countrySurface == country.countrySurface && countryPopulation == country.countryPopulation && countryId.equals(country.countryId) && countryName.equals(country.countryName) && countryAbbreviation.equals(country.countryAbbreviation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, countryName, countrySurface, countryPopulation, countryAbbreviation);
    }
}
