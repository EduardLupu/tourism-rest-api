package com.example.app.repository;

import com.example.app.model.Country;
import com.example.app.model.CountryAvgDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("SELECT NEW com.example.app.model.CountryAvgDTO(c.countryId, c.countryName, AVG(v.moneySpent)) " +
            "FROM Country c " +
            "JOIN c.visits v " +
            "GROUP BY c.countryId " +
            "ORDER BY AVG(v.moneySpent) DESC ")
    List<CountryAvgDTO> OrderCountriesByAverageMoneySpent();

    // Show all countries order desc by the average money spent in visits
}
