package com.example.app.controller;

import com.example.app.dto.VisitDTO;
import com.example.app.dto.VisitDTOTouristDTOCountryDTO;
import com.example.app.model.Visit;
import com.example.app.service.VisitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class VisitController {

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/tourist-country")
    public ResponseEntity<List<VisitDTO>> getVisits() {
        List<VisitDTO> visits = visitService.getVisits();
        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    @GetMapping("/tourist-country/{id}")
    public ResponseEntity<VisitDTOTouristDTOCountryDTO> getVisitById(@PathVariable Long id) {
        VisitDTOTouristDTOCountryDTO visit = visitService.getVisitByIdWithDTOs(id);
        return new ResponseEntity<>(visit, HttpStatus.OK);
    }

    @PostMapping("/tourist-country")
    public ResponseEntity<Visit> create(@RequestBody VisitDTO visitDTO) {
        Visit visit = visitService.createVisit(visitDTO);
        return new ResponseEntity<>(visit, HttpStatus.CREATED);
    }

    @PutMapping("/tourist-country/{id}")
    public ResponseEntity<Visit> update(@PathVariable Long id,@Valid @RequestBody Visit visit) {
        Visit visit1 = visitService.updateVisit(id, visit);
        return new ResponseEntity<>(visit1, HttpStatus.OK);
    }


    @DeleteMapping("/tourist-country/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        visitService.deleteVisit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
