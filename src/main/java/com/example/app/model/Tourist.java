package com.example.app.model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Entity
public class Tourist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long touristId;
    private String touristName;
    private Date touristDateOfBirth;

    @Transient
    private int age;

    private int computeAge(LocalDate touristDateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(touristDateOfBirth, currentDate).getYears();
    }


}
