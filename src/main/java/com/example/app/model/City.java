package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;
    private String cityName;
    private int citySurface;
    private int cityPopulation;
    private int cityPostalCode;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    public City() {

    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCitySurface() {
        return citySurface;
    }

    public void setCitySurface(int citySurface) {
        this.citySurface = citySurface;
    }

    public int getCityPopulation() {
        return cityPopulation;
    }

    public void setCityPopulation(int cityPopulation) {
        this.cityPopulation = cityPopulation;
    }

    public int getCityPostalCode() {
        return cityPostalCode;
    }

    public void setCityPostalCode(int cityPostalCode) {
        this.cityPostalCode = cityPostalCode;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return citySurface == city.citySurface && cityPopulation == city.cityPopulation && cityPostalCode == city.cityPostalCode && cityId.equals(city.cityId) && cityName.equals(city.cityName) && country.equals(city.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId, cityName, citySurface, cityPopulation, cityPostalCode, country);
    }
}
