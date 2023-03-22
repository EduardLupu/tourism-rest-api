package com.example.app.repository;

import com.example.app.model.Country;
import com.example.app.model.CountryStatisticsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("SELECT NEW com.example.app.model.CountryStatisticsDTO(c.countryId, c.countryName, AVG(v.daysSpent)) " +
            "FROM Country c " +
            "JOIN c.visits v " +
            "GROUP BY c.countryId " +
            "ORDER BY AVG(v.daysSpent) DESC ")
    List<CountryStatisticsDTO> orderCountriesByAverageDaysSpent();
    // Show all countries order desc by the average days spent in visits

}
