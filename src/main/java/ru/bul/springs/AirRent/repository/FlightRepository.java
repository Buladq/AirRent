package ru.bul.springs.AirRent.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bul.springs.AirRent.models.Flight;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Integer> {

    @Override
    @Query(value = "select * from flight where accepted_by_admin=true",nativeQuery = true)
    List<Flight> findAll();

    @Query(value = "select*from flight where team_of_pilots=:idteam and accepted_by_admin=true;",nativeQuery = true)
    Page<Flight> findByIDTeam(@Param("idteam") int idteam, Pageable pageable);
}
