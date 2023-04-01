package com.example.app.service;

import com.example.app.dto.TouristStatisticsDTO;
import com.example.app.model.Tourist;
import com.example.app.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {

    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public List<Tourist> getTourists() {
        return touristRepository.findAll();
    }

    public Tourist getTouristById(Long id) {
        return touristRepository.findById(id).orElse(null);
    }

    public Tourist createTourist(Tourist tourist) {
        return touristRepository.save(tourist);
    }

    public Tourist updateTourist(Long id, Tourist tourist) {
        if (touristRepository.existsById(id)) {
            Tourist touristToBeUpdated = getTouristById(id);
            touristToBeUpdated.setTouristName(tourist.getTouristName());
            touristToBeUpdated.setTouristDateOfBirth(tourist.getTouristDateOfBirth());
            touristToBeUpdated.setTouristGender(touristToBeUpdated.getTouristGender());
            return touristRepository.save(touristToBeUpdated);
        }
        return null;
    }

    public boolean deleteTourist(Long id) {
        if (touristRepository.existsById(id)) {
            touristRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<TouristStatisticsDTO> getTouristsInDescOrderBasedOnTotalMoneySpent() {
        return touristRepository.orderTouristsByTheTotalMoneySpentInVisits();
    }
}
