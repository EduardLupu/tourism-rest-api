package com.example.app.controller;

import com.example.app.model.Country;
import com.example.app.model.Tourist;
import com.example.app.model.Visit;
import com.example.app.model.VisitDTO;
import com.example.app.service.CountryService;
import com.example.app.service.TouristService;
import com.example.app.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private TouristService touristService;

    @GetMapping("/visits")
    public ResponseEntity<List<Visit>> getVisits() {
        List<Visit> visits = visitService.getVisits();
        if (visits.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    @GetMapping("/visits/{id}")
    public ResponseEntity<Visit> getVisitById(@PathVariable Long id) {
        Visit visit = visitService.getVisitById(id);
        if (visit == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(visit, HttpStatus.OK);
    }

    @PostMapping("/visits")
    public ResponseEntity<Visit> create(@RequestBody VisitDTO v) {
        Tourist tourist = touristService.getTouristById(v.touristId());
        Country country = countryService.getCountryById(v.countryId());
        if (tourist != null && country != null) {
            Visit visit = new Visit(tourist, country, v.moneySpent(), v.daysSpent());
            return new ResponseEntity<>(visitService.createVisit(visit), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/visits/{id}")
    public ResponseEntity<Visit> update(@PathVariable Long id, @RequestBody Visit visit) {
        Visit visit1 = visitService.updateVisit(id, visit);
        if (visit1 == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(visit1, HttpStatus.OK);
    }


    @DeleteMapping("/visits/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        if (visitService.deleteVisit(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
