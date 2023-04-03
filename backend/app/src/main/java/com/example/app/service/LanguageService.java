package com.example.app.service;

import com.example.app.exceptions.ResourceNotFoundException;
import com.example.app.model.Language;
import com.example.app.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }


    public List<Language> getLanguages() {
        return languageRepository.findAll();
    }

    public Language getLanguageById(Long id) {
        return languageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Language with id " + id + " not found!"));
    }

    public Language createLanguage(Language language) {
        return languageRepository.save(language);
    }

    public Language updateLanguage(Long id, Language language) {
        Language language1 = getLanguageById(id);
        language1.setLanguageName(language.getLanguageName());
        language1.setLanguageAbbreviation(language.getLanguageAbbreviation());
        language1.setNativeSpeakers(language.getNativeSpeakers());
        return languageRepository.save(language1);
    }

    public void deleteById(Long id) {
        Language language = getLanguageById(id);
        languageRepository.delete(language);
    }

}
