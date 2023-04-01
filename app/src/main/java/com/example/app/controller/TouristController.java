package com.example.app.controller;

import com.example.app.dto.TouristDTO;
import com.example.app.dto.TouristStatisticsDTO;
import com.example.app.model.Tourist;
import com.example.app.service.TouristService;
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
        List<TouristDTO> tourists = touristService.getTourists()
                .stream()
                .map(e -> new TouristDTO(e.getTouristId(),
                        e.getTouristName(),
                        e.getTouristDateOfBirth(),
                        e.getTouristGender(),
                        e.getAge()))
                .toList();

        if (tourists.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(tourists, HttpStatus.OK);
    }

    @GetMapping("/tourists/stats")
    public ResponseEntity<List<TouristStatisticsDTO>> getTouristsStats() {
        List<TouristStatisticsDTO> tourists = touristService.getTouristsInDescOrderBasedOnTotalMoneySpent();
        if (tourists.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(tourists, HttpStatus.OK);
    }

    @GetMapping("/tourists/{id}")
    public ResponseEntity<Tourist> getTourist(@PathVariable Long id) {
        Tourist tourist = touristService.getTouristById(id);
        if (tourist == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(tourist, HttpStatus.OK);
    }

    @PostMapping("/tourists")
    public ResponseEntity<Tourist> create(@RequestBody Tourist tourist) {
        return new ResponseEntity<>(touristService.createTourist(tourist), HttpStatus.CREATED);
    }

    @PutMapping("/tourists/{id}")
    public ResponseEntity<Tourist> update(@PathVariable Long id, @RequestBody Tourist tourist) {
        Tourist tourist1 = touristService.updateTourist(id, tourist);
        if (tourist == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(tourist1, HttpStatus.OK);
    }

    @DeleteMapping("/tourists/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        if (touristService.deleteTourist(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
