package com.example.app.repository;

import com.example.app.model.TouristStatisticsDTO;
import com.example.app.model.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TouristRepository extends JpaRepository<Tourist, Long> {


    // Show all tourists in descending order based on the money spent in all of their visits.

    @Query("SELECT NEW com.example.app.model.TouristStatisticsDTO(t.touristId, t.touristName, t.touristDateOfBirth, t.touristGender, SUM(v.moneySpent)) " +
            "FROM Tourist t " +
            "JOIN t.visits v " +
            "GROUP BY t.touristId " +
            "ORDER BY SUM(v.moneySpent) DESC"
    )
    List<TouristStatisticsDTO> orderTouristsByTheTotalMoneySpentInVisits();
}
