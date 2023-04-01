package com.example.app.controller;

import com.example.app.dto.TouristDTO;
import com.example.app.dto.TouristStatisticsDTO;
import com.example.app.model.Tourist;
import com.example.app.service.TouristService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("/tourists")
    public ResponseEntity<List<TouristDTO>> getTourists() {
        List<TouristDTO> tourists = touristService.getTourists();
        return new ResponseEntity<>(tourists, HttpStatus.OK);
    }

    @GetMapping("/tourists/order-by-money-spent")
    public ResponseEntity<List<TouristStatisticsDTO>> getTouristsStats() {
        List<TouristStatisticsDTO> tourists = touristService.getTouristsInDescOrderBasedOnTotalMoneySpent();
        return new ResponseEntity<>(tourists, HttpStatus.OK);
    }

    @GetMapping("/tourists/{id}")
    public ResponseEntity<Tourist> getTourist(@PathVariable Long id) {
        Tourist tourist = touristService.getTouristById(id);
        return new ResponseEntity<>(tourist, HttpStatus.OK);
    }

    @PostMapping("/tourists")
    public ResponseEntity<Tourist> create(@Valid @RequestBody Tourist tourist) {
        return new ResponseEntity<>(touristService.createTourist(tourist), HttpStatus.CREATED);
    }

    @PutMapping("/tourists/{id}")
    public ResponseEntity<Tourist> update(@PathVariable Long id, @Valid @RequestBody Tourist tourist) {
        Tourist tourist1 = touristService.updateTourist(id, tourist);
        return new ResponseEntity<>(tourist1, HttpStatus.OK);
    }

    @DeleteMapping("/tourists/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        touristService.deleteTourist(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
