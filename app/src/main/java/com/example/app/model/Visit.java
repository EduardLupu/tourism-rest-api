package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tourist_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Tourist tourist;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Country country;

    @Min(value = 0, message = "Error: moneySpent shouldn't be negative!")
    private int moneySpent;
    @Min(value = 0, message = "Error: daysSpent shouldn't be negative!")
    private int daysSpent;

    public Visit(Tourist tourist, Country country, int moneySpent, int daysSpent) {
        this.tourist = tourist;
        this.country = country;
        this.moneySpent = moneySpent;
        this.daysSpent = daysSpent;
    }

    public Visit() {
    }

    public Long getTouristId() {
        return tourist.getTouristId();
    }

    public String getTouristName() {
        return tourist.getTouristName();
    }

    public String getCountryName() {
        return country.getCountryName();
    }

    public Long getCountryId() {
        return country.getCountryId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tourist getTourist() {
        return tourist;
    }

    public void setTourist(Tourist tourist) {
        this.tourist = tourist;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(int moneySpent) {
        this.moneySpent = moneySpent;
    }

    public int getDaysSpent() {
        return daysSpent;
    }

    public void setDaysSpent(int daysSpent) {
        this.daysSpent = daysSpent;
    }
}
