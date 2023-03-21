package com.example.app.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tourist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long touristId;
    private String touristName;
    private LocalDate touristDateOfBirth;
    private String touristGender;
    @Transient
    private int age;

    @OneToMany(mappedBy = "tourist")
    private List<Visit> visits = new ArrayList<>();

    public int getAge() {
        LocalDate now = LocalDate.now();
        return Period.between(touristDateOfBirth, now).getYears();
    }

    public Tourist(Long touristId, String touristName, LocalDate touristDateOfBirth, String touristGender) {
        this.touristId = touristId;
        this.touristName = touristName;
        this.touristDateOfBirth = touristDateOfBirth;
        this.touristGender = touristGender;
    }

    public Tourist() {

    }

    public Long getTouristId() {
        return touristId;
    }

    public void setTouristId(Long touristId) {
        this.touristId = touristId;
    }

    public String getTouristName() {
        return touristName;
    }

    public void setTouristName(String touristName) {
        this.touristName = touristName;
    }

    public LocalDate getTouristDateOfBirth() {
        return touristDateOfBirth;
    }

    public void setTouristDateOfBirth(LocalDate touristDateOfBirth) {
        this.touristDateOfBirth = touristDateOfBirth;
    }

    public String getTouristGender() {
        return touristGender;
    }

    public void setTouristGender(String touristGender) {
        this.touristGender = touristGender;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> countries) {
        this.visits = countries;
    }
}
