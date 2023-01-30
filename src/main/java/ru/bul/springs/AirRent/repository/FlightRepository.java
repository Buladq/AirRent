package ru.bul.springs.AirRent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bul.springs.AirRent.models.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Integer> {
}
