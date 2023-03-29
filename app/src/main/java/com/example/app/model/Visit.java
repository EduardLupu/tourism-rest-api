package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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

    public Long gettId() {
        return tourist.getTouristId();
    }

    public Long getcId() {
        return country.getCountryId();
    }

    @Transient
    private Long tId;
    @Transient
    private Long cId;

    private int moneySpent;
    private int daysSpent;

    public Visit(Tourist tourist, Country country, int moneySpent, int daysSpent) {
        this.tourist = tourist;
        this.country = country;
        this.moneySpent = moneySpent;
        this.daysSpent = daysSpent;
    }

    public Visit() {

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
