package com.example.app.controller;

import com.example.app.model.Language;
import com.example.app.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getLanguages() {
        List<Language> languages = languageService.getLanguages();
        if (languages.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(languages, HttpStatus.OK);
    }

    @GetMapping("/languages/{id}")
    public ResponseEntity<Language> getLanguagesById(@PathVariable Long id) {
        Language language = languageService.getLanguageById(id);
        if (language == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(language, HttpStatus.OK);
    }

    @PostMapping("/languages")
    public ResponseEntity<Language> create(@RequestBody Language language) {
        return new ResponseEntity<>(languageService.createLanguage(language), HttpStatus.CREATED);
    }

    @PostMapping("/languages/{id}")
    public ResponseEntity<Language> update(@PathVariable Long id, @RequestBody Language language) {
        Language language1 = languageService.updateLanguage(id, language);
        if (language1 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(language1, HttpStatus.OK);
    }

    @DeleteMapping("/languages/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        if (languageService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
