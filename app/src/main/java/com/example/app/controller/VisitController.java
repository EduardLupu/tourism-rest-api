package com.example.app.controller;

import com.example.app.dto.CountryDTO;
import com.example.app.dto.TouristDTO;
import com.example.app.dto.VisitDTO;
import com.example.app.dto.VisitDTOwithDTOs;
import com.example.app.model.Country;
import com.example.app.model.Tourist;
import com.example.app.model.Visit;
import com.example.app.service.CountryService;
import com.example.app.service.TouristService;
import com.example.app.service.VisitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class VisitController {

    private final VisitService visitService;

    private final CountryService countryService;

    private final TouristService touristService;

    public VisitController(VisitService visitService, CountryService countryService, TouristService touristService) {
        this.visitService = visitService;
        this.countryService = countryService;
        this.touristService = touristService;
    }

    @GetMapping("/tourist-country")
    public ResponseEntity<List<VisitDTO>> getVisits() {
        List<VisitDTO> visits = visitService.getVisits().stream().map(
                        e -> new VisitDTO(
                                e.getId(),
                                e.getTourist().getTouristId(),
                                e.getCountry().getCountryId(),
                                e.getMoneySpent(),
                                e.getDaysSpent()))
                .collect(Collectors.toList());
        if (visits.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    @GetMapping("/tourist-country/{id}")
    public ResponseEntity<VisitDTOwithDTOs> getVisitById(@PathVariable Long id) {
        Visit visit = visitService.getVisitById(id);
        if (visit == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Tourist t = visit.getTourist();
        Country c = visit.getCountry();
        VisitDTOwithDTOs visitDTO = new VisitDTOwithDTOs(
                visit.getId(),
                new TouristDTO(t.getTouristId(),
                        t.getTouristName(),
                        t.getTouristDateOfBirth(),
                        t.getTouristGender(),
                        t.getAge()),
                new CountryDTO(c.getCountryId(),
                        c.getCountryName(),
                        c.getCountrySurface(),
                        c.getCountryPopulation(),
                        c.getCountryAbbreviation()),
                visit.getMoneySpent(),
                visit.getDaysSpent()
        );

        return new ResponseEntity<>(visitDTO, HttpStatus.OK);
    }

    @PostMapping("/tourist-country")
    public ResponseEntity<Visit> create(@RequestBody VisitDTO v) {
        Tourist tourist = touristService.getTouristById(v.touristId());
        Country country = countryService.getCountryById(v.countryId());
        if (tourist != null && country != null) {
            Visit visit = new Visit(tourist, country, v.moneySpent(), v.daysSpent());
            return new ResponseEntity<>(visitService.createVisit(visit), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/tourist-country/{id}")
    public ResponseEntity<Visit> update(@PathVariable Long id, @RequestBody Visit visit) {
        Visit visit1 = visitService.updateVisit(id, visit);
        if (visit1 == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(visit1, HttpStatus.OK);
    }


    @DeleteMapping("/tourist-country/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        if (visitService.deleteVisit(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
