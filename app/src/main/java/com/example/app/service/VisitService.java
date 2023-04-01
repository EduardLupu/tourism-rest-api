package com.example.app.service;

import com.example.app.dto.CountryDTO;
import com.example.app.dto.TouristDTO;
import com.example.app.dto.VisitDTO;
import com.example.app.dto.VisitDTOTouristDTOCountryDTO;
import com.example.app.exceptions.ResourceNotFoundException;
import com.example.app.model.Country;
import com.example.app.model.Tourist;
import com.example.app.model.Visit;
import com.example.app.repository.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {
  
    private final VisitRepository visitRepository;

    private final TouristService touristService;

    private final CountryService countryService;

    public VisitService(VisitRepository visitRepository, TouristService touristService, CountryService countryService) {
        this.visitRepository = visitRepository;
        this.touristService = touristService;
        this.countryService = countryService;
    }

    public List<VisitDTO> getVisits() {
        return visitRepository.findAll()
                .stream().map(
                        visit -> new VisitDTO(
                                visit.getId(),
                                visit.getTourist().getTouristId(),
                                visit.getCountry().getCountryId(),
                                visit.getMoneySpent(),
                                visit.getDaysSpent()))
                .collect(Collectors.toList());
    }

    public Visit getVisitById(Long id) {
        return visitRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Visit with id " + id + " not found!"));
    }

    public VisitDTOTouristDTOCountryDTO getVisitByIdWithDTOs(Long id) {
        Visit visit = getVisitById(id);
        Tourist t = visit.getTourist();
        Country c = visit.getCountry();
        return new VisitDTOTouristDTOCountryDTO(
                visit.getId(),
                new TouristDTO(t.getTouristId(),
                        t.getTouristName(),
                        t.getTouristDateOfBirth(),
                        t.getTouristGender(),
                        t.getTouristAge()),
                new CountryDTO(c.getCountryId(),
                        c.getCountryName(),
                        c.getCountryPopulation(),
                        c.getCountrySurface(),
                        c.getCountryAbbreviation()),
                visit.getMoneySpent(),
                visit.getDaysSpent()
        );
    }

    public Visit createVisit(VisitDTO visitDTO) {
        Tourist tourist = touristService.getTouristById(visitDTO.touristId());
        Country country = countryService.getCountryById(visitDTO.countryId());
        Visit visit = new Visit(tourist, country, visitDTO.moneySpent(), visitDTO.daysSpent());
        return visitRepository.save(visit);
    }

    public Visit updateVisit(Long id, Visit visit) {
        Visit visit1 = getVisitById(id);
        visit1.setDaysSpent(visit.getDaysSpent());
        visit1.setMoneySpent(visit.getMoneySpent());
        return visitRepository.save(visit1);
    }

    public void deleteVisit(Long id) {
        Visit visit = getVisitById(id);
        visitRepository.delete(visit);
    }
}
