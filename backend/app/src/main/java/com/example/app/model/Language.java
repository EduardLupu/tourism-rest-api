package com.example.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long languageId;

    @NotBlank(message = "Error: languageName shouldn't be blank!")
    private String languageName;
    @NotBlank(message = "Error: languageAbbreviation shouldn't be blank!")
    private String languageAbbreviation;
    @Min(value = 0, message = "Error: nativeSpeakers shouldn't be negative!")
    private int nativeSpeakers;

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageAbbreviation() {
        return languageAbbreviation;
    }

    public void setLanguageAbbreviation(String languageAbbreviation) {
        this.languageAbbreviation = languageAbbreviation;
    }

    public int getNativeSpeakers() {
        return nativeSpeakers;
    }

    public void setNativeSpeakers(int nativeSpeakers) {
        this.nativeSpeakers = nativeSpeakers;
    }
}
