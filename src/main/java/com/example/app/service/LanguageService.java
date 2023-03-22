package com.example.app.service;

import com.example.app.model.Language;
import com.example.app.model.Tourist;
import com.example.app.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public List<Language> getLanguages() {
        return languageRepository.findAll();
    }

    public Language getLanguageById(Long id) {
        return languageRepository.findById(id).orElse(null);
    }

    public Language createLanguage(Language language) {
        return languageRepository.save(language);
    }

    public Language updateLanguage(Long id, Language language) {
        if (languageRepository.existsById(id)) {
            Language language1 = getLanguageById(id);
            language1.setLanguageName(language.getLanguageName());
            language1.setLanguageAbbreviation(language.getLanguageAbbreviation());
            language1.setNativeSpeakers(language.getNativeSpeakers());
            return languageRepository.save(language1);
        }
        return null;
    }

    public boolean deleteById(Long id) {
        if (languageRepository.existsById(id)) {
            languageRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
