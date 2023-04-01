package com.example.app.dto;

import java.time.LocalDate;

public record TouristDTO(Long touristId, String touristName, LocalDate touristDateOfBirth, String touristGender, int age) {
}
