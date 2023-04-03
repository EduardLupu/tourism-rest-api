package com.example.app.controller;

import com.example.app.model.Language;
import com.example.app.service.LanguageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api")
@CrossOrigin
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getLanguages() {
        List<Language> languages = languageService.getLanguages();
        return new ResponseEntity<>(languages, HttpStatus.OK);
    }

    @GetMapping("/languages/{id}")
    public ResponseEntity<Language> getLanguagesById(@PathVariable Long id) {
        Language language = languageService.getLanguageById(id);
        return new ResponseEntity<>(language, HttpStatus.OK);
    }

    @PostMapping("/languages")
    public ResponseEntity<Language> create(@Valid @RequestBody Language language) {
        return new ResponseEntity<>(languageService.createLanguage(language), HttpStatus.CREATED);
    }

    @PostMapping("/languages/{id}")
    public ResponseEntity<Language> update(@PathVariable Long id, @Valid @RequestBody Language language) {
        Language language1 = languageService.updateLanguage(id, language);
        return new ResponseEntity<>(language1, HttpStatus.OK);
    }

    @DeleteMapping("/languages/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        languageService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
