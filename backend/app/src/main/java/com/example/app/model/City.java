package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;
    @NotBlank(message = "Error: cityName shouldn't be blank!")
    private String cityName;
    @Min(value = 0, message = "Error: citySurface shouldn't be negative!")
    private int citySurface;
    @Min(value = 0, message = "Error: cityPopulation shouldn't be negative!")
    private int cityPopulation;
    @Min(value = 0, message = "Error: cityPostalCode shouldn't be negative!")
    private int cityPostalCode;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    public City() {

    }

    public Long getCityId() {
        return cityId;
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
}
