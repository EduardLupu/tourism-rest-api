package com.example.app.service;

import com.example.app.dto.TouristDTO;
import com.example.app.dto.TouristStatisticsDTO;
import com.example.app.exceptions.ResourceNotFoundException;
import com.example.app.model.Tourist;
import com.example.app.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TouristService {

    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public List<TouristDTO> getTourists() {
        return touristRepository.findAll()
                .stream()
                .map(tourist -> new TouristDTO(tourist.getTouristId(),
                        tourist.getTouristName(),
                        tourist.getTouristDateOfBirth(),
                        tourist.getTouristGender(),
                        tourist.getTouristAge()))
                .collect(Collectors.toList());
    }

    public Tourist getTouristById(Long id) {
        return touristRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tourist with id " + id + " not found!"));
    }

    public Tourist createTourist(Tourist tourist) {
        return touristRepository.save(tourist);
    }

    public Tourist updateTourist(Long id, Tourist tourist) {
        Tourist touristToBeUpdated = getTouristById(id);
        touristToBeUpdated.setTouristName(tourist.getTouristName());
        touristToBeUpdated.setTouristDateOfBirth(tourist.getTouristDateOfBirth());
        touristToBeUpdated.setTouristGender(touristToBeUpdated.getTouristGender());
        return touristRepository.save(touristToBeUpdated);
    }

    public void deleteTourist(Long id) {
        Tourist tourist = getTouristById(id);
        touristRepository.delete(tourist);
    }

    public List<TouristStatisticsDTO> getTouristsInDescOrderBasedOnTotalMoneySpent() {
        return touristRepository.orderTouristsByTheTotalMoneySpentInVisits();
    }
}
