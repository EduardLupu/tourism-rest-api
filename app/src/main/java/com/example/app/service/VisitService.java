package com.example.app.service;

import com.example.app.model.Visit;
import com.example.app.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;

    public List<Visit> getVisits() {
        return visitRepository.findAll();
    }

    public Visit getVisitById(Long id) {
        return visitRepository.findById(id).orElse(null);
    }

    public Visit createVisit(Visit visit) {
        return visitRepository.save(visit);
    }

    public Visit updateVisit(Long id, Visit visit) {
        if (visitRepository.existsById(id)) {
            Visit visit1 = getVisitById(id);
            visit1.setDaysSpent(visit.getDaysSpent());
            visit1.setMoneySpent(visit.getMoneySpent());
            return visitRepository.save(visit1);
        }
        return null;
    }

    public boolean deleteVisit(Long id) {
        if (visitRepository.existsById(id)) {
            visitRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
